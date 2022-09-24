package com.wjy.atom.server.resources;

import com.wjy.atom.config.annotation.Config;
import com.wjy.atom.server.domain.User;
import com.wjy.atom.server.domain.UserInfo;
import com.wjy.atom.server.service.DeMoService;
import com.wjy.atom.server.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("api")
public class LoginResources {

    private UserInfo userInfo;

    @Inject
    private DeMoService deMoService;

    @Inject
    private UserService userService;

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public UserInfo login(@FormParam("username") String username, @FormParam("password") String password) {
        System.out.println(deMoService.getUserInfo());
        UserInfo userInfo = new UserInfo(username, password, "xxxxx@xx.com", new Date());
        deMoService.setUserInfo(userInfo);
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

    @POST
    @Path("getuser")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@FormParam("id") Integer id) {
        User user = userService.getUser(id);
        return user;
    }

}
