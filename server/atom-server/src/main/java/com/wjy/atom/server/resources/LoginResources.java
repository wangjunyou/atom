package com.wjy.atom.server.resources;

import com.wjy.atom.config.annotation.Config;
import com.wjy.atom.server.model.UserInfo;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("api")
public class LoginResources {

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public UserInfo login(@FormParam("username") String username, @FormParam("password") String password) {
        UserInfo userInfo = new UserInfo(username, password, "xxxxx@xx.com");
        System.out.println(userInfo.toString());
        return userInfo;
    }

    @Inject
    @Config("atom.version")
    private String value;

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String getDeMo() {
        value += "1";
        return "get Demo: " + value;
    }

}
