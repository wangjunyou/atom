package com.wjy.ioi.runtime.web.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;

import java.io.File;

public class HttpServer {

    private ServerBootstrap serverBootstrap;

    private String webAddress;

    private int webPort;

    public File webDir;

    public HttpServer(EventLoopGroup group, EventLoopGroup worker, Class<? extends  ServerSocketChannel> serverSocketChannel, ChannelInitializer<SocketChannel> initializer, int port){
        this.serverBootstrap = new ServerBootstrap()
                .group(group,worker)
                .channel(serverSocketChannel)
                .childHandler(initializer);
    }


    public void start() {

    }

    public void stop() {

    }

}
