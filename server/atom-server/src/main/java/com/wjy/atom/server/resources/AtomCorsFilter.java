package com.wjy.atom.server.resources;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import javax.ws.rs.ext.Provider;


@Provider
public class AtomCorsFilter extends CorsFilter {
    public AtomCorsFilter() {
        super.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        super.setAllowedHeaders("*");
        super.getAllowedOrigins().add("*");
    }
}
