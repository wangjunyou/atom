package com.wjy.atom.remote.codec;

import com.wjy.atom.remote.netty.protocol.NettyProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandlerTest extends SimpleChannelInboundHandler<NettyProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NettyProtocol msg) throws Exception {
        System.out.println("hello");
        System.out.println(msg.getBody().toString());
    }
}
