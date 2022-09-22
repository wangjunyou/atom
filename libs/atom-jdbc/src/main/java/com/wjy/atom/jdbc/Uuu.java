package com.wjy.atom.jdbc;


class AA {
    private String name;

    public void setName(String name) {
        try {
            throw new Exception("error");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
public class Uuu {

    public static void main(String[] args) {
        AA a = new AA();
        a.setName("zhangsan");
        System.out.println("=-===========");
    }
}
