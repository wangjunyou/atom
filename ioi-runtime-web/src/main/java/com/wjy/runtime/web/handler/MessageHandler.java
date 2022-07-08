package com.wjy.runtime.web.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class MessageHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

    private static final HttpDataFactory DATA_FACTORY = new DefaultHttpDataFactory(false);

    private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            HttpMethod method = req.method();
            if (method == HttpMethod.GET) {
                QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
                LOG.info("decoder map {}",decoder.parameters());
                decoder.parameters().forEach((k, v) -> System.out.println(k + "===" + v));
            } else if (method == HttpMethod.POST) {
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(DATA_FACTORY, req);
                List<InterfaceHttpData> httpDatas = decoder.getBodyHttpDatas();
                if (!httpDatas.isEmpty()) {
//                    LOG.info("uri is: {}",req);
                    LOG.info("header is: {}",req.headers());
                    for (InterfaceHttpData httpData : httpDatas) {
                        if (httpData.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                            MemoryAttribute attribute = (MemoryAttribute) httpData;
                            LOG.info("attribute map {}",attribute);
                            System.out.println(attribute.getName() + "===" + attribute.getValue());
                        }
                    }
                }else {
                    QueryStringDecoder decoder1 = new QueryStringDecoder(req.uri());
                    LOG.info("decoder map {}",decoder1.parameters());
                    decoder1.parameters().forEach((k,v) -> System.out.println(k+"==="+v));
                }
            }
            FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), OK,
                    Unpooled.wrappedBuffer(CONTENT));
            response.headers()
                    .set(CONTENT_TYPE, TEXT_PLAIN)
                    .setInt(CONTENT_LENGTH, response.content().readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
