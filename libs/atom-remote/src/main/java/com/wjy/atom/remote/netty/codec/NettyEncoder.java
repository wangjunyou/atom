package com.wjy.atom.remote.netty.codec;

import com.wjy.atom.remote.netty.protocol.NettyProtocol;
import com.wjy.atom.remote.serializer.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * NettyProtocol序列化类
 * */
public class NettyEncoder extends MessageToByteEncoder<NettyProtocol<Object>> {

    private static final ProtostuffSerializer SERIALIZER = new ProtostuffSerializer();

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyProtocol<Object> msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(msg.getVersion());
        byteBuf.writeLong(msg.getClientId());
        byteBuf.writeLong(msg.getServerId());
        ProtostuffSerializer SERIALIZER = new ProtostuffSerializer();
        byte[] data = SERIALIZER.serialize(msg.getBody());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
