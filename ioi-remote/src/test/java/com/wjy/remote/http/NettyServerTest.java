package com.wjy.remote.http;

import com.wjy.remote.netty.config.NettyConfig;
import com.wjy.remote.netty.NettyServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.junit.jupiter.api.Test;

public class NettyServerTest {

    @Test
    public void httpServerStart() throws InterruptedException {
        NettyConfig config = new NettyConfig();
        NettyServer server = new NettyServer(config);
        server.initHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new ChunkedWriteHandler())
                        .addLast(new DeMoHandler());
            }
        });
        server.start();
        Thread.sleep(1000000);
    }

    @Test
    public void tcpServerStart() throws InterruptedException {
        NettyConfig config = new NettyConfig();
        NettyServer server = new NettyServer(config);
        server.initHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline()
                        .addLast(new StringDecoder())
                        .addLast(new StringEncoder())
                        .addLast(new EchoServerHandler());
            }
        });
        server.start();
        Thread.sleep(1000000);
    }
}
