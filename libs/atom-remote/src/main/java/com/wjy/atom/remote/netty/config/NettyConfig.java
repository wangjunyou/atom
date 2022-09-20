package com.wjy.atom.remote.netty.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * client配置文件
 */

public class NettyConfig {

    private static final Integer CPUS = Runtime.getRuntime().availableProcessors();

    public static final String SERVER_THREAD_GROUP_NAME = "IOI Netty Server";
    public static final String CLIENT_THREAD_GROUP_NAME = "IOI Netty Client";

    /**
     * Client连接超时时间
     */
    private int connectTimeoutMillis = 3000;

    /**
     * 数据积压
     */
    private int soBacklog = 1024;

    /**
     * 连接端口
     */
    private int port = 12345;

    /**
     * Nagle算法
     */
    private boolean tcpNoDelay = true;

    /**
     * 心跳保持
     */
    private boolean soKeepalive = true;

    /**
     * 发送缓冲区大小
     */
    private int sendBufferSize = 65535;

    /**
     * 接收缓冲区大小
     */
    private int receiveBufferSize = 65535;

    /**
     * 服务端线程
     */
    private int serverNumThreads = CPUS;

    /**
     * 客户端线程
     */
    private int clientNumThreads = CPUS;

    /**
     * 传输类型
     */
    public enum TransportType {
        NIO,
        EPOLL,
        AUTO
    }

    /**
     * 传输类型
     */
    private TransportType transportType = TransportType.AUTO;

    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    public int getSoBacklog() {
        return soBacklog;
    }

    public void setSoBacklog(int soBacklog) {
        this.soBacklog = soBacklog;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public boolean isSoKeepalive() {
        return soKeepalive;
    }

    public void setSoKeepalive(boolean soKeepalive) {
        this.soKeepalive = soKeepalive;
    }

    public int getSendBufferSize() {
        return sendBufferSize;
    }

    public void setSendBufferSize(int sendBufferSize) {
        this.sendBufferSize = sendBufferSize;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    public int getServerNumThreads() {
        return serverNumThreads;
    }

    public void setServerNumThreads(int serverNumThreads) {
        this.serverNumThreads = serverNumThreads;
    }

    public int getClientNumThreads() {
        return clientNumThreads;
    }

    public void setClientNumThreads(int clientNumThreads) {
        this.clientNumThreads = clientNumThreads;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

}
