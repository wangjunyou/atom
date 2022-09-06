package com.wjy.api.common.remote;

import java.net.SocketAddress;
import java.util.concurrent.Future;

/**
 * 远程服务client
 * */
public interface Client {
    Future  connect(SocketAddress socketAddress);
    void stop();

}
