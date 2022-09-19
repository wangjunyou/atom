package com.wjy.atom.config;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private Date births;

    public User(int id, String name, Date births) {
        this.id = id;
        this.name = name;
        this.births = births;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", births=" + births +
                '}';
    }
}
