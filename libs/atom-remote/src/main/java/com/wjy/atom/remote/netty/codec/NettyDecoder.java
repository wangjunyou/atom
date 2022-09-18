package com.wjy.atom.remote.netty.codec;

import com.wjy.atom.remote.netty.protocol.NettyProtocol;
import com.wjy.atom.remote.serializer.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * NettyProtocol反序列化类
 * */
public class NettyDecoder extends ByteToMessageDecoder {

    private static final ProtostuffSerializer SERIALIZER = new ProtostuffSerializer();

    /** 反序列化的类 */
    private Class<?> clazz;

    public NettyDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int version = byteBuf.readInt();
        long clientId = byteBuf.readLong();
        long serverId = byteBuf.readLong();
        int msgLength = byteBuf.readInt();
        byte[] msg = new byte[msgLength];
        byteBuf.readBytes(msg);
        Object obj = SERIALIZER.deserialize(msg, clazz);
        NettyProtocol protocol = new NettyProtocol(version, clientId, serverId, msgLength, obj);
        list.add(protocol);
    }
}
