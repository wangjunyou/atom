package com.wjy.remote.http;

import com.wjy.remote.netty.config.NettyConfig;
import com.wjy.remote.netty.NettyClient;
import io.netty.channel.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;

public class NettyClientTest {

    @Test
    public void httpClientConnect() throws InterruptedException, IOException {
        NettyConfig config = new NettyConfig();
        NettyClient client = new NettyClient(config);
        client.initHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline()
                        .addLast(new LoggingHandler())
                        .addLast(new StringDecoder())
                        .addLast(new StringEncoder())
                        .addLast(new EchoClientHandler());
            }
        });
        SocketAddress socketAddress = new InetSocketAddress("localhost", config.getPort());
        ChannelFuture connect = client.connect(socketAddress);
        Channel channel = connect.sync().channel();
        for (int i = 0; i < 100; i++) {
            channel.writeAndFlush("Client out:" + i);
        }
//        channel.closeFuture().sync();
        new Thread(new Runnable() {
            NettyClient nettyClient = client;

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                nettyClient.stop();
            }
        }).start();
        channel.closeFuture().syncUninterruptibly();
    }
}
