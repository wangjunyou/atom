package com.wjy.http.controller;

import com.wjy.http.annotation.Get;
import com.wjy.http.annotation.Name;
import com.wjy.http.annotation.Path;
import com.wjy.http.annotation.Post;
import io.netty.handler.codec.http.multipart.FileUpload;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Path("/hello")
@Singleton
public class HelloController {

    private Hello hello;

    private String name;
    private int age;

    @Get("/get")
    public Hello getAcC() {
        Map m = new HashMap();
        m.put("hello","vaule");
        hello = new Hello(1234,"acc",m);
        return hello;
    }

    @Get("/name")
    public String getName() {
        return "demo";
    }

    @Get("/value")
    public String getVaule(String name, String age) {
        String result = "name: " + name + ", age:" + age;
        System.out.println(result);
        return result;
    }

    @Post("/post")
    public String getPost(String name, String age) {
        String result = "name: " + name + ", age:" + age;
        System.out.println(result);
        return result;
    }

    @Post("/up")
    public String getUp(String name, FileUpload fileUpload) {
        File file = new File("/Users/jocker/opt/workspace/git/ioi/ioi-http/src/main/resources/"+fileUpload.getFilename());
        try {
            fileUpload.renameTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileUpload.getFilename();
    }

    @Post("/setd")
    public String setData(String name, int age) {
        this.name = name;
        this.age = age;
        return "ok";
    }

    @Post("/getd")
    public Hello getData(String name) {
        if(this.name==null) return null;
        if(this.name.equals(name)) {
            Hello c = new Hello();
            c.setName(name);
            c.setId(123456);
            return c;
        }
        return null;
    }

    @Post("/obj")
    public String getObj(@Name("data") Hello hello) {
        System.out.println(hello.toString());
        return hello.toString();
    }
}
