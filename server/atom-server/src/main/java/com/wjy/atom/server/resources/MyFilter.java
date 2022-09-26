package com.wjy.atom.server.resources;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Request;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Collection;


@Provider
public class MyFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        System.out.println("My Filter");
    }
}
