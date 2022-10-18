package com.wjy.atom.http;

import com.google.common.net.InetAddresses;
import com.google.inject.Binding;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.wjy.atom.config.annotation.Config;
import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.plugins.guice.ModuleProcessor;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.InjectorFactory;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.util.GetRestful;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.Provider;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HttpServer {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServer.class);

    private static final boolean SSL = System.getProperty("ssl") != null;
    private static final Inet4Address DEFAULT_IPV4 = InetAddresses.fromIPv4BigInteger(BigInteger.ZERO);
    private static final Inet6Address DEFAULT_IPV6 = InetAddresses.fromIPv6BigInteger(BigInteger.ZERO);

    @Inject
    @Config("atom.http.port")
    private String httpPort;

    @Inject
    @Config("atom.root_path")
    private String rootPath;

    private NettyJaxrsServer server;
    private Injector injector;

    public void start() {
        start(IpProtocol.IPV4);
    }

    public HttpServer initInjector(Injector injector) {
        this.injector = injector;
        return this;
    }

    public void start(IpProtocol protocol) {

        LOG.info("HttpServer start.");

        String httpFormat = "http://%s:" + httpPort + rootPath;
        switch (protocol) {
            case IPV4:
                String ipv4HostAddress = DEFAULT_IPV4.getHostAddress();
                URI u4 = URI.create(String.format(httpFormat, ipv4HostAddress));
                LOG.info("Http address: {}", u4.toString());
                start(u4);
                break;
            case IPV6:
                String ipv6HostAddress = DEFAULT_IPV6.getHostAddress();
                URI u6 = URI.create(String.format(httpFormat, "[" + ipv6HostAddress + "]"));
                LOG.info("Http address: {}", u6.toString());
                start(u6);
                break;
            default:
                LOG.error("Ip protocol Must be IPV4 or IPV6.");
                break;
        }
    }

    public void start(URI uri) {

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setWiderRequestMatching(true);
        deployment.setStatisticsEnabled(true);

        List<Object> providers = new ArrayList<>();
        List<ResourceFactory> resourceFactories = new ArrayList();
        Iterator<Binding<?>> bindings = injector.getBindings().values().iterator();
        while (bindings.hasNext()) {
            Binding<?> binding = bindings.next();
            Class<?> beanClass = binding.getKey().getTypeLiteral().getRawType();
            if (beanClass instanceof Class) {
                if (GetRestful.isRootResource(beanClass)) {
                    resourceFactories.add(new GuiceResourceFactory(binding.getProvider(), beanClass));
                }
                if(beanClass.isAnnotationPresent(Provider.class)){
                    providers.add(binding.getProvider().get());
                }
            }
        }
        deployment.setResourceFactories(resourceFactories);
        deployment.setProviders(providers);

        server = new NettyJaxrsServer();
        server.setDeployment(deployment);

        String host = uri.getHost();
        int port = uri.getPort();
        String path = uri.getPath();
        server.setHostname(host);
        server.setPort(port);
        server.setRootResourcePath(path);
        server.start();

//        ModuleProcessor processor = new ModuleProcessor(deployment.getRegistry(), deployment.getProviderFactory());
//        processor.processInjector(injector);

    }

    public void stop() {
        if (server != null) {
            LOG.info("HttpServer stop.");
            server.stop();
        }
    }

    enum IpProtocol {
        IPV4,
        IPV6
    }

}
