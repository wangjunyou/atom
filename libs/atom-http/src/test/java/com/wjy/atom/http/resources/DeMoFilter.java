package com.wjy.atom.http.resources;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/** ContainerRequestFilter/ContainerResponseFilter */
@Provider
public class DeMoFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println("request DeMoFilter");
    }
}
