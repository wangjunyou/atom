package com.wjy.atom.server;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.wjy.atom.config.module.ConfigModule;
import com.wjy.atom.mybatis.module.MybatisModule;
import com.wjy.atom.server.service.MenuService;
import com.wjy.atom.server.service.RoleService;
import com.wjy.atom.server.service.UserService;
import com.wjy.atom.server.service.impl.MenuServiceImpl;
import com.wjy.atom.server.service.impl.RoleServiceImpl;
import com.wjy.atom.server.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceInject {

    public static Injector getInjector() {
        InputStream inputStream = Server.class.getResourceAsStream("/atom-conf.properties");
        Properties props = new Properties();
        try {
            props.load(inputStream);
        } catch (IOException e) {
            System.exit(-1);
        }
        return Guice.createInjector(new ConfigModule(props), new MybatisModule("test", "com.wjy.atom"),new AbstractModule() {
            @Override
            protected void configure() {
                bind(UserService.class).to(UserServiceImpl.class).in(Scopes.SINGLETON);
                bind(MenuService.class).to(MenuServiceImpl.class).in(Scopes.SINGLETON);
                bind(RoleService.class).to(RoleServiceImpl.class).in(Scopes.SINGLETON);
            }
        });
    }
}
