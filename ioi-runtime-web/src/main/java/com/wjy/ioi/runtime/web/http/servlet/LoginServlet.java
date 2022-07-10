package com.wjy.ioi.runtime.web.http.servlet;

import com.wjy.ioi.runtime.web.handler.IoiServlet;
import com.wjy.ioi.runtime.web.http.annotation.Mapping;
import com.wjy.ioi.runtime.web.http.annotation.ParameterName;
import com.wjy.ioi.runtime.web.http.annotation.Post;

@Mapping("/login")
public class LoginServlet implements IoiServlet {

    @Mapping("/add")
    @Post
    public String addUser() {
        return "addUser";
    }

    @Mapping("/edit")
    @Post
    public String editUser() {
        return "editUser";
    }

    @Mapping("/delete")
    @Post
    public String deleteUser(@ParameterName("name") String userName) {
        return "deleteUser";
    }

    @Mapping("/delete2")
    @Post
    public String deleteUser(@ParameterName("name") String userName, int id) {
        return "deleteUser";
    }
}
