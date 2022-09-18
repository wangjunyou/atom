package com.wjy.http;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wjy.http.module.HttpModule;
import org.junit.jupiter.api.Test;


public class HttpServerTest {

    @Test
    public void test() throws InterruptedException {
        Injector injector = Guice.createInjector(new HttpModule("com.wjy.http"));
        HttpServer server = injector.getInstance(HttpServer.class);
        server.start(HttpServer.IpProtocol.IPV4);
        Thread.sleep(1000000);
        server.stop();
    }
}
