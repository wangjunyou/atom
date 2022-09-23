package com.wjy.atom.jdbc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wjy.atom.config.module.ConfigModule;
import com.wjy.atom.jdbc.annotation.Column;
import com.wjy.atom.jdbc.annotation.Table;
import com.wjy.atom.jdbc.manager.EntityManager;
import com.wjy.atom.jdbc.module.JdbcModule;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.locale.converters.IntegerLocaleConverter;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Table("db_user")
class User {

    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private Date births;
    @Column
    private String addr;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirths() {
        return births;
    }

    public void setBirths(Date births) {
        this.births = births;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", births=" + births +
                ", addr='" + addr + '\'' +
                '}';
    }
}

public class JdbcModuleTest {

    private Injector injector;

    @BeforeEach
    public void injector() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("./atom-conf.properties");
        Properties props = new Properties();
        props.load(inputStream);
        injector = Guice.createInjector(new ConfigModule(props), new JdbcModule());
    }


    @Test
    public void test() throws IOException {
        EntityManager instance = injector.getInstance(EntityManager.class);
        User user = new User();
        user.setId(6);
        User u = instance.selectOne(User.class, user);
        System.out.println(u.getBirths().getTime());
        System.out.println(u.toString());
    }

    @Test
    public void test1() {
        EntityManager instance = injector.getInstance(EntityManager.class);
        User user = new User();
        user.setId(6);
        user.setName("zhangsan");
        List<User> users = instance.selectList(User.class, user);
        users.forEach(u -> System.out.println(u.toString()));
    }

    @Test
    public void test2() {
        Object i = 12345l;
        System.out.println((i instanceof Integer));
    }

}
