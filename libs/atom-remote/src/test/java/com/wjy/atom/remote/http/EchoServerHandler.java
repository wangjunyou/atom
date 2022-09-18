package com.wjy.atom.remote.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String o) throws Exception {
        System.out.println(o+"\n");
        ctx.writeAndFlush("server out"+o +"\n");
    }
}
