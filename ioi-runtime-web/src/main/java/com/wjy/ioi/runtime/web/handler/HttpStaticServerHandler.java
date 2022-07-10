package com.wjy.ioi.runtime.web.handler;

import com.wjy.ioi.runtime.web.handler.util.MimeTypes;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 静态web加载服务
 */

public class HttpStaticServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger LOG = LoggerFactory.getLogger(HttpStaticServerHandler.class);

    private final File webDir;

    public HttpStaticServerHandler(File webDir) {
        this.webDir = webDir;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {
            HttpRequest currentRequest = (HttpRequest) msg;
            if (currentRequest.method() == HttpMethod.GET) {
                QueryStringDecoder decoder = new QueryStringDecoder(currentRequest.uri());
                String requestPath = decoder.path();
                respondWithFile(ctx, currentRequest, requestPath);
            } else {
                ctx.fireChannelRead(currentRequest);
            }
        } else {
            ctx.fireChannelRead(ReferenceCountUtil.retain(msg));
        }
    }

    /* 本方法未包括cache,后期添加压缩算法 */
    public void respondWithFile(ChannelHandlerContext ctx, HttpRequest request, String requestPath) {
        if (requestPath.endsWith("/")) {
            requestPath += "index.html";
        }

        ClassLoader classLoader = HttpStaticServerHandler.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("web" + requestPath);
        if (inputStream == null) {
            ctx.fireChannelRead(request);
            return;
        }

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

        HttpHeaders headers = response.headers();
        String fileName = requestPath.substring(requestPath.lastIndexOf("/") + 1);
        String mimeType = MimeTypes.getMimeTypeForFileName(fileName);
        mimeType = mimeType == null ? MimeTypes.getDefaultMimeType() : mimeType;
        headers.set(HttpHeaderNames.CONTENT_TYPE, mimeType);

        if (HttpUtil.isKeepAlive(request)) {
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        try {
            byte[] datas = inputStream.readAllBytes();
            response.content().writeBytes(datas);
            headers.set(HttpHeaderNames.CONTENT_LENGTH, datas.length);
        } catch (IOException e) {
            //error
            e.printStackTrace();
        }

        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(request))
            channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}
