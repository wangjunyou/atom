package com.wjy.http.netty;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wjy.core.inject.PackageFinder;
import com.wjy.http.annotation.Path;
import com.wjy.http.inject.HttpModule;
import com.wjy.http.netty.handler.ControllerHandler;
import com.wjy.http.netty.handler.HttpServerStaticFileHandler;
import com.wjy.remote.netty.NettyServer;
import com.wjy.remote.netty.config.NettyConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.util.Set;


public class WebServer {

    public static void main(String[] args) throws InterruptedException {
        NettyConfig config = new NettyConfig();
        NettyServer server = new NettyServer(config);
        PackageFinder finder = new PackageFinder()
                .toPackage("com.wjy.http");
        Set<Class<?>> paths = finder.getTypesAnnotatedWith(Path.class);
        Injector injector = Guice.createInjector(new HttpModule(paths));
        server.initHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new CorsHandler(CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build()))
                        .addLast(new ChunkedWriteHandler())
//                        .addLast(new StaticFileHandler())
                        .addLast(new HttpServerStaticFileHandler(new File("/Users/jocker/opt/workspace/git/ioi/ioi-http/src/main/resources/data")))
                        .addLast(new ControllerHandler(injector, "com.wjy.http"));
            }
        });
        server.start();
        Thread.sleep(1000000);
    }

}
