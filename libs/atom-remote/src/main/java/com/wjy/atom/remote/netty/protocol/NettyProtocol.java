package com.wjy.atom.remote.netty.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * netty自定义协议
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NettyProtocol<T> {

    private int version;
    private long serverId;
    private long clientId;
    private int msgLength;
    private T body;

}
