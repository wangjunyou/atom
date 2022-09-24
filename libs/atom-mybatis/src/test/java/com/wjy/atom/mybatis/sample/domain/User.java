package com.wjy.atom.mybatis.sample.domain;

import java.util.Date;

public class User {

    private Integer id;
    private String name;
    private Date births;
    private String addr;

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
