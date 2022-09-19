package com.wjy.atom.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wjy.atom.config.module.ConfigModule;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AtomConfigTest {

    @Test
    public void atomConfig() throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File("/home/jocker/opt/workspaces/git/atom/libs/atom-config/src/test/resources/testConfig.properties")));
        AtomConfig config = AtomConfig.fromProp(prop);
        System.out.println(config.toString());
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            System.out.println(entry.getValue().getClass().getName());
        }
        config.set("atom.id", 123456);
        config.set("atom.version", 123.123);
        Integer integer = config.getInteger("atom.id");
        Double aDouble = config.getDouble("atom.version");
        System.out.println(integer.toString() + "==" + aDouble.toString());
//        config.set("atom.error", "hello world");
//        Double aDouble1 = config.getDouble("atom.error");
//        System.out.println(aDouble1.toString());
        Object string = "hello world";
        System.out.println(string instanceof String);
    }

    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("atom.string", "atom.string");
        map.put("atom.double", 123.223d);
        map.put("atom.char", "c");
        map.put("atom.float", 1.1f);
        map.put("atom.byte", Byte.valueOf("0"));
        map.put("atom.boolean", true);
        map.put("atom.long", 123456767l);
        ConfigModule configModule = new ConfigModule(map);
        Injector injector = Guice.createInjector(configModule);
        UserService instance = injector.getInstance(UserService.class);
        System.out.println(instance.toString());
    }


    @Test
    public void test1() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", User.class);
        AtomConfig config = AtomConfig.fromMap(map);
        Class user = config.getClass("user");
        System.out.println(user.getName());
    }

}
