package com.wjy.atom.remote.http;

import com.wjy.atom.remote.netty.config.NettyConfig;
import com.wjy.atom.remote.netty.NettyServer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.jupiter.api.Test;

public class NettyServerTest {

    @Test
    public void tcpServerStart() throws InterruptedException {
        NettyConfig config = new NettyConfig();
        NettyServer server = new NettyServer(config);
        server.initHandler(new StringDecoder(),
                new StringEncoder(),
                new EchoServerHandler());
        server.start();
        Thread.sleep(1000000);
    }
}
