package com.wjy.runtime.web.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.ReferenceCountUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class HttpRequestHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final HttpDataFactory DATA_FACTORY = new DefaultHttpDataFactory(true);

    private final File tmpDir;

    private HttpPostRequestDecoder currentDecoder;

    private HttpRequest currentRequest;
    private String currentRequestPath;

    public HttpRequestHandler(File tmpDir) {
        this.tmpDir = tmpDir;
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (currentDecoder != null) {
            currentDecoder.cleanFiles();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        try {
            if (msg instanceof HttpRequest) {

                if (currentDecoder != null) {
                    currentDecoder.destroy();
                    currentDecoder = null;
                }

                currentRequest = (HttpRequest) msg;
                HttpMethod method = currentRequest.method();
                if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
                    ctx.fireChannelRead(currentRequest);
                } else if (method == HttpMethod.POST) {
                    currentRequestPath = new QueryStringDecoder(currentRequest.uri(), StandardCharsets.UTF_8).path();
                    currentDecoder = new HttpPostRequestDecoder(DATA_FACTORY, currentRequest, StandardCharsets.UTF_8);
                } else {
                    throw new IOException("Unsupported HTTP method: " + method.name());
                }
            } else if (currentDecoder != null && msg instanceof HttpContent) {
                HttpContent chunk = (HttpContent) msg;
                currentDecoder.offer(chunk);

                QueryStringEncoder encoder = new QueryStringEncoder(currentRequestPath);
                try {
//                    HttpPostRequestEncoder encoder = new HttpPostRequestEncoder(DATA_FACTORY,currentRequest,false);
                    while (currentDecoder.hasNext()) {
                        InterfaceHttpData data = currentDecoder.next();

                        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload && tmpDir != null) {
                            DiskFileUpload file = (DiskFileUpload) data;
                            if (file.isCompleted()) {
                                String name = file.getFilename();
                                File target = new File(tmpDir, UUID.randomUUID() + "_" + name);
                                if (!tmpDir.exists()) {
                                    tmpDir.mkdirs();
                                }
                                file.renameTo(target);
                                encoder.addParam(file.getName() + "_filepath", target.getAbsolutePath());
                                encoder.addParam(file.getName(), name);

                            }
                        } else if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                            DiskAttribute attr = (DiskAttribute) data;
                            encoder.addParam(attr.getName(), attr.getValue());
                        }
                    }
                } catch (HttpPostRequestDecoder.EndOfDataDecoderException ignored) {
                }
                currentRequest.setUri(encoder.toString());

                if (chunk instanceof LastHttpContent) {
                    HttpRequest request = currentRequest;
                    currentRequest = null;
                    currentRequestPath = null;

                    currentDecoder.destroy();
                    currentDecoder = null;
                    ctx.fireChannelRead(request);
                    ctx.fireChannelRead(ReferenceCountUtil.retain(chunk));
                }
            } else {
                ctx.fireChannelRead(ReferenceCountUtil.retain(msg));
            }
        } catch (Throwable t) {

            currentRequest = null;
            currentRequestPath = null;
            if (currentDecoder != null) {
                currentDecoder.destroy();
                currentDecoder = null;
            }

            if (ctx.channel().isActive()) {
                byte[] bytes = t.getMessage().getBytes(StandardCharsets.UTF_8);
                DefaultFullHttpResponse response =
                        new DefaultFullHttpResponse(
                                HttpVersion.HTTP_1_1,
                                HttpResponseStatus.INTERNAL_SERVER_ERROR,
                                Unpooled.wrappedBuffer(bytes));

                response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
                response.headers()
                        .set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

                ctx.writeAndFlush(response);
            }
        }
    }
}
