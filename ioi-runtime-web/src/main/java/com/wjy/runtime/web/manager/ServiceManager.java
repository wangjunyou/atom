package com.wjy.runtime.web.manager;

public class ServiceManager {

    public static void main(String[] args) {
        String str = "hello/zhangsan";
        String substring = str.substring(str.lastIndexOf("/"));
        System.out.println(substring);
    }
}
