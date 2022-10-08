package com.wjy.atom.core.util;

import org.junit.jupiter.api.Test;

import javax.naming.Name;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class ReflectUtilsTest {


    @Test
    public void getField() {
        Field name = ReflectUtils.getField(ReflectUser.class, "name");
        System.out.println(name.getName());
    }

    @Test
    public void wirteAndReadField() throws NoSuchFieldException, IllegalAccessException {
        ReflectUser user = new ReflectUser();
        ReflectUtils.writeField(user, "name", "hello world");
        Object name = ReflectUtils.readField(user, "name");
        System.out.println(name);
    }

    @Test
    public void invokeConstructor() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object[] objs = new Object[3];
        objs[0] = 1;
        objs[1] = "zhangsan";
        objs[2] = new Date();
        ReflectUser user = ReflectUtils.invokeConstructor(ReflectUser.class, objs, Integer.class, String.class, Date.class);
        System.out.println(user.toString());
    }

    @Test
    public void invokeMethod() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ReflectUser user = new ReflectUser(1,"zhangsan",new Date());
        Object name = ReflectUtils.invokeMethod(user, "getName", null);
        System.out.println(name);
    }
}
