package com.wjy.atom.http;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wjy.atom.config.module.ConfigModule;
import com.wjy.atom.http.module.HttpModule;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Http3Test {

    @Test
    public void test() throws IOException, InterruptedException {
        Properties props = new Properties();
        props.load(new FileInputStream("/home/jocker/opt/workspaces/git/atom/libs/atom-http3/src/test/resources/atom.properties"));
        Injector injector = Guice.createInjector(new ConfigModule(props), new HttpModule("com.wjy.atom.http3"));
        HttpServer httpServer = injector.getInstance(HttpServer.class);
        httpServer.initInjector(injector);
        httpServer.start();
        Thread.currentThread().join();
    }
}
