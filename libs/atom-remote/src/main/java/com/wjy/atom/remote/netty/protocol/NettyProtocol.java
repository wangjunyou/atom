package com.wjy.atom.remote.netty.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * netty自定义协议
 * */
public class NettyProtocol<T> {

    private int version;
    private long serverId;
    private long clientId;
    private int msgLength;
    private T body;

    public NettyProtocol() {
    }

    public NettyProtocol(int version, long serverId, long clientId, int msgLength, T body) {
        this.version = version;
        this.serverId = serverId;
        this.clientId = clientId;
        this.msgLength = msgLength;
        this.body = body;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
