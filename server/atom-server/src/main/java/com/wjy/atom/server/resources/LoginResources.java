package com.wjy.atom.server.resources;

import com.wjy.atom.config.annotation.Config;
import com.wjy.atom.server.domain.User;
import com.wjy.atom.server.service.DeMoService;
import com.wjy.atom.server.service.UserService;
import com.wjy.atom.server.util.Result;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("api")
public class LoginResources {

    @Inject
    private DeMoService deMoService;

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
    @Path("getuser")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@FormParam("id") Integer id) {
        User user = userService.getUser(id);
        System.out.println(user.toString());
        return user;
    }

    @POST
    @Path("getuser2")
    @Produces(MediaType.APPLICATION_JSON)
    public Result getUser2(@FormParam("id") Integer id) {
        User user = userService.getUser(id);
        System.out.println(user.toString());
        Result<User> result = new Result<>();
        result.setCode(200);
        result.setMsg("ok");
        result.setData(user);
        return result;
    }


}
