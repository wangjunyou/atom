package com.wjy.atom.remote.codec;

import com.wjy.atom.remote.netty.protocol.NettyProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcServerHandlerTest extends SimpleChannelInboundHandler<NettyProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyProtocol msg) throws Exception {
        System.out.println(msg.getBody().toString());
        ctx.writeAndFlush(msg);
    }
}
