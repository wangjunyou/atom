package com.wjy.atom.http.resources;

import javax.inject.Singleton;

@Singleton
public class DemoSrevice {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
