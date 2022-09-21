package com.wjy.atom.http.resources;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("demo")
public class DemoResources {

    @Inject
    private DemoSrevice demoSrevice;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDemo() {
        System.out.println(demoSrevice.getName());
        demoSrevice.setName(
                demoSrevice.getName() == null ?
                        "" :
                        demoSrevice.getName() +1
        );
        return "getDemo" + demoSrevice.getName();
    }
}
