package com.wjy.runtime.web;

import com.wjy.runtime.web.handler.HttpFileUploadHandler;
import com.wjy.runtime.web.handler.HttpStaticServerHandler;
import com.wjy.runtime.web.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;

public class IoiServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap sb = new ServerBootstrap();
        sb.group(group,worker);
        sb.channel(NioServerSocketChannel.class);
        sb.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new CorsHandler(CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build()))
                        .addLast(new HttpStaticServerHandler(new File("/tmp")))
                        .addLast(new HttpFileUploadHandler(new File("/tmp")))
                        .addLast(new HttpObjectAggregator(65536))
                        .addLast(new ChunkedWriteHandler())
                        .addLast(new MessageHandler());
            }
        });
        sb.bind(2222).sync().channel();
    }
}
