package com.wjy.http;

import com.wjy.http.controller.Bc;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DeMoTest {

    @Test
    public void test() {
        Class<Bc> aClass = Bc.class;
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            System.out.println(method.getName()+"====");
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName());
            }
        }
    }
}
