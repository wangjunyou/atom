package com.wjy.atom.remote.netty.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * client配置文件
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NettyConfig {

    private static final Integer CPUS = Runtime.getRuntime().availableProcessors();

    public static final String SERVER_THREAD_GROUP_NAME = "IOI Netty Server";
    public static final String CLIENT_THREAD_GROUP_NAME = "IOI Netty Client";

    /** Client连接超时时间 */
    private int connectTimeoutMillis = 3000;

    /** 数据积压 */
    private int soBacklog = 1024;

    /** 连接端口 */
    private int port = 12345;

    /** Nagle算法 */
    private boolean tcpNoDelay = true;

    /** 心跳保持 */
    private boolean soKeepalive = true;

    /** 发送缓冲区大小 */
    private int sendBufferSize = 65535;

    /** 接收缓冲区大小 */
    private int receiveBufferSize = 65535;

    /** 服务端线程 */
    private int serverNumThreads = CPUS;

    /** 客户端线程 */
    private int clientNumThreads = CPUS;

    /** 传输类型 */
    public enum TransportType {
        NIO,
        EPOLL,
        AUTO
    }

    /** 传输类型 */
    private TransportType transportType = TransportType.AUTO;
}
