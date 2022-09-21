package com.wjy.atom.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wjy.atom.config.module.ConfigModule;
import com.wjy.atom.http.HttpServer;
import com.wjy.atom.http.module.HttpModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {

        InputStream inputStream = Server.class.getResourceAsStream("/atom-conf.properties");
        Properties props = new Properties();
        try {
            props.load(inputStream);
        } catch (IOException e) {
            LOG.error("Atom config file load failed.  {}", e);
            System.exit(-1);
        }

        Injector injector = Guice.createInjector(
                new ConfigModule(props),
                new HttpModule("com.wjy.atom")
        );

        try {
            HttpServer httpServer = injector.getInstance(HttpServer.class);
            httpServer
                    .initInjector(injector)
                    .start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                httpServer.stop();
            }));
        } catch (Exception e) {
            LOG.error("Atom httpServer run failed. {}", e);
            System.exit(-1);
        }
    }
}
