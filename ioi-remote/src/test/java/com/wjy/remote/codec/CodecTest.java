package com.wjy.remote.codec;

import com.wjy.remote.netty.NettyClient;
import com.wjy.remote.netty.NettyServer;
import com.wjy.remote.netty.codec.NettyDecoder;
import com.wjy.remote.netty.codec.NettyEncoder;
import com.wjy.remote.netty.config.NettyConfig;
import com.wjy.remote.netty.protocol.NettyProtocol;
import com.wjy.remote.serializer.DataType;
import com.wjy.remote.serializer.ProtostuffSerializer;
import com.wjy.remote.serializer.Sdata;
import com.wjy.remote.serializer.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;

public class CodecTest {

    @Test
    public void nettyCodec() throws InterruptedException, IOException {
        NettyConfig sconfig = new NettyConfig();
        NettyServer server = new NettyServer(sconfig);
        server.initHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        .addLast(new NettyDecoder(Sdata.class))
                        .addLast(new NettyEncoder())
                        .addLast(new RpcServerHandlerTest());
            }
        });
        server.start();
        NettyConfig cconfig = new NettyConfig();
        NettyClient client = new NettyClient(cconfig);
        client.initHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        .addLast(new NettyDecoder(Sdata.class))
                        .addLast(new NettyEncoder())
                        .addLast(new RpcClientHandlerTest());
            }
        });
        SocketAddress address = new InetSocketAddress("localhost", cconfig.getPort());
        ChannelFuture connect = client.connect(address);
        Channel channel = connect.sync().channel();
        for (int i = 0; i < 10; i++) {
            User user = new User(i,"user"+i, new Date(), "dizhi"+i, DataType.ONE);
            Object sdata = new Sdata(user);
            ProtostuffSerializer protostuffSerializer = new ProtostuffSerializer();
            byte[] serialize = protostuffSerializer.serialize(sdata);
            int version = 1;
            long serverId = 1l;
            long clientId = 1l;
            int len = serialize.length;
            NettyProtocol protocol = new NettyProtocol(version, serverId, clientId, len, sdata);
            channel.writeAndFlush(protocol);
        }
        Thread.sleep(4000);
        client.stop();
        server.stop();
    }
}
