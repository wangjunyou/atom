package com.wjy.atom.server.resources;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjy.atom.config.annotation.Config;
import com.wjy.atom.server.domain.User;
import com.wjy.atom.server.service.UserService;
import com.wjy.atom.server.util.Result;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
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
    public List<User> queryUserName(@FormParam("name") String name) {
        User user = new User();
        user.setId(10);
        user.setUserName("zhangsan");
        user.setUserPassword("123456");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userService.insertUser(user);
        return userService.queryUserByName(name);
    }


}
