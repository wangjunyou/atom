package com.wjy.http;

import com.google.common.net.InetAddresses;
import io.netty.channel.Channel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.net.ssl.SSLException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.URI;
import java.security.cert.CertificateException;


/**
 * http服务器
 * */
public class HttpServer {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServer.class);

    private static final boolean SSL = System.getProperty("ssl") != null;
    private static final int DEFAULT_PORT = 12345;
    private static final Inet4Address DEFAULT_IPV4 = InetAddresses.fromIPv4BigInteger(BigInteger.ZERO);
    private static final Inet6Address DEFAULT_IPV6 = InetAddresses.fromIPv6BigInteger(BigInteger.ZERO);
    private ResourceConfig config;
    private Channel serverChannel;

    @Inject
    public HttpServer(ResourceConfig config) {
        this.config = config;
    }

    public void start(IpProtocol protocol) {

        LOG.info("HttpServer start.");

        String httpFormat = "http://%s:" + DEFAULT_PORT + "/";
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

        final SslContext sslCtx;
        if (SSL) {
            try {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            } catch (CertificateException e) {
                throw new RuntimeException(e);
            } catch (SSLException e) {
                throw new RuntimeException(e);
            }
        } else {
            sslCtx = null;
        }
        serverChannel = NettyHttpContainerProvider.createHttp2Server(uri, config, sslCtx);
    }

    public void stop() {
        if (serverChannel != null) {
            LOG.info("HttpServer stop.");
            serverChannel.close();
        }
    }

    enum IpProtocol {
        IPV4,
        IPV6
    }
}
