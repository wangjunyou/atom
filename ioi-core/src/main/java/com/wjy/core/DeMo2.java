package com.wjy.core;

import java.util.Date;

public class DeMo2 {
    private static final Date d = new Date();

    private String name;

    public DeMo2(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DeMo2{" +
                "name='" + name + '\'' +
                "date=" + d.getTime()+"" +
                '}';
    }
}
