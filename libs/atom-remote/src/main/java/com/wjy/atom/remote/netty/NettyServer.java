package com.wjy.atom.remote.netty;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wjy.atom.api.common.remote.Server;
import com.wjy.atom.remote.netty.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * httpServer服务器
 */

public class NettyServer implements Server {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

    public static final ThreadFactoryBuilder THREAD_FACTORY_BUILDER =
            new ThreadFactoryBuilder()
                    .setDaemon(true);

    private final NettyConfig config;
    private ServerBootstrap bootstrap;
    private ChannelFuture bindFuture;

    public NettyServer(NettyConfig config) {
        this.config = config;
        bootstrap = new ServerBootstrap();
        switch (config.getTransportType()) {
            case NIO:
                initNioBootstrap();
                break;
            case EPOLL:
                initEpollBootstrap();
                break;
            case AUTO:
                if (Epoll.isAvailable()) {
                    initEpollBootstrap();
                    LOG.info("Transport type 'auto': using EPOLL.");
                } else {
                    initNioBootstrap();
                    LOG.info("Transport type 'auto': using NIO.");
                }
                break;
        }
        bootstrap
                .localAddress("localhost", config.getPort())
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, config.getSoBacklog())
                .childOption(ChannelOption.SO_KEEPALIVE, config.isSoKeepalive())
                .childOption(ChannelOption.TCP_NODELAY, config.isTcpNoDelay())
                .childOption(ChannelOption.SO_SNDBUF, config.getSendBufferSize())
                .childOption(ChannelOption.SO_RCVBUF, config.getReceiveBufferSize());
    }

    @Override
    public void start() {
        try {
            bindFuture = bootstrap.bind().sync();
        } catch (Exception e) {
            LOG.error("NettyServer bind fail {}, exit", e.getMessage(), e);
            throw new RuntimeException(String.format("NettyServer bind %s fail", config.getPort()));
        }
        if (bindFuture.isSuccess()) {
            LOG.info("NettyServer bind success at port : {}", config.getPort());
        } else if (bindFuture.cause() != null) {
            throw new RuntimeException(String.format("NettyServer bind %s fail", config.getPort()), bindFuture.cause());
        } else {
            throw new RuntimeException(String.format("NettyServer bind %s fail", config.getPort()));
        }
    }

    @Override
    public void stop() {

        if (bindFuture != null) {
            bindFuture.channel().close().awaitUninterruptibly();
            bindFuture = null;
        }

        if (bootstrap != null) {
            if (bootstrap.group() != null) {
                bootstrap.group().shutdownGracefully();
            }
            bootstrap = null;
        }

        LOG.info("NettyServer closed");
    }

    public void initHandler(ChannelHandler... channelHandlers) {


        initHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                for (ChannelHandler channelHandler : channelHandlers) {
                    pipeline.addLast(channelHandler);
                }
            }
        });

        LOG.info("channelHandler :");
        LOG.info("===========================================");
        for (ChannelHandler channelHandler : channelHandlers) {
            LOG.info("{}", channelHandler);
        }
        LOG.info("===========================================");

    }

    private void initHandler(ChannelHandler channelHandler) {
        bootstrap.childHandler(channelHandler);
    }

    /**
     * NIO Bootstrap
     */
    private void initNioBootstrap() {

        LOG.info("Server bootstrap is initNioBootstrap.");

        String name = NettyConfig.SERVER_THREAD_GROUP_NAME + " (" + config.getPort() + ")";
        NioEventLoopGroup eventLoopGroup =
                new NioEventLoopGroup(
                        config.getServerNumThreads(),
                        THREAD_FACTORY_BUILDER.setNameFormat(name + " Thread %d").build());
        bootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class);
    }

    /**
     * EPOLL Bootstrap
     */
    private void initEpollBootstrap() {

        LOG.info("Server bootstrap is initEpollBootstrap.");

        String name = NettyConfig.SERVER_THREAD_GROUP_NAME + " (" + config.getPort() + ")";
        EpollEventLoopGroup eventLoopGroup =
                new EpollEventLoopGroup(
                        config.getServerNumThreads(),
                        THREAD_FACTORY_BUILDER.setNameFormat(name + " Thread %d").build());
        bootstrap.group(eventLoopGroup).channel(EpollServerSocketChannel.class);
    }
}
