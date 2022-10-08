package com.wjy.atom.http.module;

import com.google.inject.AbstractModule;
import com.wjy.atom.core.finder.PackageFinder;
import com.wjy.atom.http.HttpServer;
import org.jboss.resteasy.plugins.guice.ext.JaxrsModule;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.Set;

public class HttpModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(HttpModule.class);

    private String pkg;

    public HttpModule(String pkg) {
        this.pkg = pkg;
    }

    @Override
    protected void configure() {

        LOG.info("Http module load.");

        install(new JaxrsModule());
        install(new RequestScopeModule());

        PackageFinder finder = new PackageFinder()
                .toPackage(pkg);
        Set<Class<?>> pathClass = finder.getTypesAnnotatedWith(Path.class);
        pathClass.forEach(clazz -> bind(clazz));

        Set<Class<?>> providerClass = finder.getTypesAnnotatedWith(Provider.class);
        providerClass.forEach(clazz -> bind(clazz));

//        bind(HttpServer.class);

    }
}
