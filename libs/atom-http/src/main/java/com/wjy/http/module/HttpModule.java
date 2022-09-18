package com.wjy.http.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.wjy.atom.core.finder.PackageFinder;
import com.wjy.http.HttpServer;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.Path;
import java.util.Set;

/**
 * http module注册类
 * */
public class HttpModule extends AbstractModule {

    private String pkg;

    public HttpModule(String pkg) {
        this.pkg = pkg;
    }

    @Override
    protected void configure() {
        PackageFinder finder = new PackageFinder()
                .toPackage(pkg);
        Set<Class<?>> resources = finder.getTypesAnnotatedWith(Path.class);
        ResourceConfig config = new ResourceConfig(resources);
        config.register(MultiPartFeature.class);
        bind(ResourceConfig.class).toInstance(config);
        bind(HttpServer.class).in(Scopes.SINGLETON);
    }
}
