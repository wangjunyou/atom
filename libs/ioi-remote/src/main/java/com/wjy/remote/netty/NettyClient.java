package com.wjy.remote.netty;

import com.wjy.api.common.remote.Client;
import com.wjy.remote.netty.config.NettyConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

import static com.wjy.remote.netty.NettyServer.THREAD_FACTORY_BUILDER;

/**
 * httpClient服务器
 */
public class NettyClient implements Client {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);

    private NettyConfig config;

    private Bootstrap bootstrap;

    public NettyClient(NettyConfig config) {
        this.config = config;
        bootstrap = new Bootstrap();
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
                .option(ChannelOption.SO_KEEPALIVE, config.isSoKeepalive())
                .option(ChannelOption.TCP_NODELAY, config.isTcpNoDelay())
                .option(ChannelOption.SO_SNDBUF, config.getSendBufferSize())
                .option(ChannelOption.SO_RCVBUF, config.getReceiveBufferSize())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeoutMillis());
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress) {
        try {
            return bootstrap.connect(socketAddress);
        } catch (ChannelException e) {
            if ((e.getCause() instanceof SocketException
                    && e.getCause().getMessage().equals("Too many open files"))
                    || (e.getCause() instanceof ChannelException
                    && e.getCause().getCause() instanceof SocketException
                    && e.getCause()
                    .getCause()
                    .getMessage()
                    .equals("Too many open files"))) {
                throw new ChannelException(
                        "The operating system does not offer enough file handles to open the network connection. "
                                + "Please increase the number of available file handles.",
                        e.getCause());
            } else {
                throw e;
            }
        }
    }

    @Override
    public void stop() {
        if (bootstrap != null) {
            if (bootstrap.group() != null) {
                bootstrap.group().shutdownGracefully();
            }
            bootstrap = null;
        }
        LOG.info("NettyClient closed");
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
        bootstrap.handler(channelHandler);
    }

    /**
     * NIO Bootstrap
     */
    private void initNioBootstrap() {
        String name = NettyConfig.SERVER_THREAD_GROUP_NAME + " (" + config.getPort() + ")";
        NioEventLoopGroup eventLoopGroup =
                new NioEventLoopGroup(
                        config.getServerNumThreads(),
                        THREAD_FACTORY_BUILDER.setNameFormat(name + " Thread %d").build());
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class);
    }

    /**
     * EPOLL Bootstrap
     */
    private void initEpollBootstrap() {
        String name = NettyConfig.SERVER_THREAD_GROUP_NAME + " (" + config.getPort() + ")";
        EpollEventLoopGroup eventLoopGroup =
                new EpollEventLoopGroup(
                        config.getServerNumThreads(),
                        THREAD_FACTORY_BUILDER.setNameFormat(name + " Thread %d").build());
        bootstrap.group(eventLoopGroup).channel(EpollSocketChannel.class);
    }
}
