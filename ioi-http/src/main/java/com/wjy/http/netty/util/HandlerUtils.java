package com.wjy.http.netty.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Handler response 工具类
 */
public class HandlerUtils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    public static CompletableFuture<Void> sendResponse(
            ChannelHandlerContext ctx,
            boolean keepAlive,
            String message,
            HttpResponseStatus respStatus,
            Map<AsciiString, AsciiString> headers) {

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, respStatus);

        if (headers != null)
            for (Map.Entry<AsciiString, AsciiString> header : headers.entrySet()) {
                response.headers().set(header.getKey(), header.getValue());
            }

        byte[] buf = message.getBytes(DEFAULT_CHARSET);
        ByteBuf b = Unpooled.copiedBuffer(buf);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.length);
        response.content().writeBytes(b);

        return sendResponse(ctx, keepAlive, response);

    }

    /*public static CompletableFuture<Void> sendResponse(
            ChannelHandlerContext ctx,
            boolean keepAlive,
            String message,
            HttpResponseStatus respStatus,
            Map<String, String> headers) {

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, respStatus);

        if (headers != null)
            for (Map.Entry<String, String> header : headers.entrySet()) {
                response.headers().set(header.getKey(), header.getValue());
            }

        byte[] buf = message.getBytes(DEFAULT_CHARSET);
        ByteBuf b = Unpooled.copiedBuffer(buf);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.length);
        response.content().writeBytes(b);

        return sendResponse(ctx, keepAlive, response);

    }*/

    public static CompletableFuture<Void> sendResponse(
            ChannelHandlerContext ctx,
            boolean keepAlive,
            HttpResponse response) {
        if (keepAlive)
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);


        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        // close the connection, if no keep-alive is needed
        if (!keepAlive) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }

        return toCompletableFuture(lastContentFuture);
    }

    /**
     * 下载文件
     */
    public static CompletableFuture<Void> transferFile(ChannelHandlerContext ctx, boolean keepAlive, File file) {

        return null;
    }

    private static CompletableFuture<Void> toCompletableFuture(final ChannelFuture channelFuture) {
        final CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        channelFuture.addListener(
                future -> {
                    if (future.isSuccess()) {
                        completableFuture.complete(null);
                    } else {
                        completableFuture.completeExceptionally(future.cause());
                    }
                });
        return completableFuture;
    }
}
