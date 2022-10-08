package com.wjy.atom.mybatis.sample;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wjy.atom.config.module.ConfigModule;
import com.wjy.atom.mybatis.module.MybatisModule;
import com.wjy.atom.mybatis.sample.domain.User;
import com.wjy.atom.mybatis.sample.service.UserService;
import com.wjy.atom.mybatis.sample.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MybatisModuleTest {

    private Injector injector;
    private UserService userService;

    @BeforeEach
    public void init() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("./atom-conf.properties");
        Properties props = new Properties();
        props.load(inputStream);
        injector = Guice.createInjector(new ConfigModule(props),
                new MybatisModule("test", "com.wjy.atom.mybatis.sample.mapper"),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(UserService.class).to(UserServiceImpl.class);
                    }
                });
        userService = injector.getInstance(UserService.class);
    }

    @Test
    public void getUser() {
        for (int i = 1; i <= 6; i++) {
            User user = userService.getUser(i);
            System.out.println(user.toString());
        }
    }
}
