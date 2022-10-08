package com.wjy.atom.core.util;

import java.util.Date;

public class ReflectUser {

    private Integer id;
    private String name;
    private Date births;

    public ReflectUser() {
    }

    public ReflectUser(Integer id, String name, Date births) {
        this.id = id;
        this.name = name;
        this.births = births;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return "ReflectUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", births=" + births +
                '}';
    }
}
