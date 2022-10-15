package com.wjy.atom.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.inject.Injector;
import com.wjy.atom.server.ServiceInject;
import com.wjy.atom.server.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void before() {
        Injector injector = ServiceInject.getInjector();
        userService = injector.getInstance(UserService.class);
    }

    @Test
    public void selectUserByName() {
        List<User> users = userService.selectUserByName("zhangsan");
        users.forEach(u -> System.out.println(u.toString()));

    }

    @Test
    public void selectUserByNamePaging() {
        PageHelper.startPage(1,20);
        List<User> users = userService.selectUserByName("zhangsan");
        PageInfo<User> pageInfo = new PageInfo<>(users);
        System.out.println(pageInfo.toString());
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsan");
        user.setUserPassword("admin");
        user.setPhone("13453848");
        user.setUpdateTime(new Date());
        userService.update(user);
    }

    @Test
    public void updateUserIfNecessary() {
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsan");
        user.setUserPassword("qwertyyuiop");
        user.setPhone("13453848");
        user.setUpdateTime(new Date());
        userService.updateIfNecessary(user);
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
        userService.delete(user.getId());
        userService.insert(user);
    }

}
