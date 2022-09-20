package com.wjy.atom.server.resources;

import com.wjy.atom.config.annotation.Config;
import com.wjy.atom.server.model.UserInfo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("atom/api")
public class LoginResources {

    @POST
    @Path("xxx")
    @Produces(MediaType.APPLICATION_JSON)
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
