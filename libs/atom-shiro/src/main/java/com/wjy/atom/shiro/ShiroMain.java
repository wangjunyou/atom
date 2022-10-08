package com.wjy.atom.shiro;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wjy.atom.config.module.ConfigModule;
import com.wjy.atom.mybatis.module.MybatisModule;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ShiroMain {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = ShiroMain.class.getClassLoader().getResourceAsStream("./atom-conf.properties");
        Properties props = new Properties();
        props.load(inputStream);
        Injector injector = Guice.createInjector(new ConfigModule(props), new MybatisModule("shiro", "com.wjy.atom"), new AtomModule());
        SecurityManager securityManager = injector.getInstance(SecurityManager.class);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("root","admin");
        subject.login(token);
        if (subject.isAuthenticated()) {
            System.out.println("hello");
        }
    }
}
