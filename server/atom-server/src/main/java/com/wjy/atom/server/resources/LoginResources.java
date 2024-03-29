package com.wjy.atom.server.resources;

import com.wjy.atom.config.annotation.Config;
import com.wjy.atom.server.domain.User;
import com.wjy.atom.server.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("api")
public class LoginResources {

    @Inject
    private UserService userService;

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
    @Path("queryUserName")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@FormParam("name") String name) {
        List<User> users = userService.selectUserByName(name);
        users.forEach(u->{
            System.out.println(u.toString());
        });
        return users;
    }


}
