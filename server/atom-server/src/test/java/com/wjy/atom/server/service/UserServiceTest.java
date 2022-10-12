package com.wjy.atom.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.wjy.atom.config.module.ConfigModule;
import com.wjy.atom.mybatis.module.MybatisModule;
import com.wjy.atom.server.Server;
import com.wjy.atom.server.domain.User;
import com.wjy.atom.server.service.impl.UserServiceMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void before() {
        InputStream inputStream = Server.class.getResourceAsStream("/atom-conf.properties");
        Properties props = new Properties();
        try {
            props.load(inputStream);
        } catch (IOException e) {
            System.exit(-1);
        }
        Injector injector = Guice.createInjector(new ConfigModule(props), new MybatisModule("test", "com.wjy.atom"),new AbstractModule() {
            @Override
            protected void configure() {
                bind(UserService.class).to(UserServiceMapperImpl.class).in(Scopes.SINGLETON);
            }
        });
        userService = injector.getInstance(UserService.class);
    }

    @Test
    public void selectUserByName() {
        List<User> users = userService.queryUserByName("zhangsan");
        users.forEach(u -> System.out.println(u.toString()));

    }

    @Test
    public void selectUserByNamePaging() {
        PageHelper.startPage(1,20);
        List<User> users = userService.queryUserByName("zhangsan");
        PageInfo<User> pageInfo = new PageInfo<>(users);
        System.out.println(pageInfo.toString());
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsan");
        user.setUserPassword("qwertyyuiop");
        user.setPhone("13453848");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userService.updateUser(user);
    }

    @Test
    public void updateUserIfNecessary() {
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsan");
        user.setUserPassword("qwertyyuiop");
        user.setPhone("13453848");
        user.setUpdateTime(new Date());
        userService.updateUserIfNecessary(user);
    }

    @Test
    public void insertUser() {
        User user = new User();
        user.setId(10);
        user.setUserName("zhangsan");
        user.setUserPassword("qwertyyuiop");
        user.setPhone("13453848");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userService.deleteUserById(user.getId());
        userService.insertUser(user);
    }

}
