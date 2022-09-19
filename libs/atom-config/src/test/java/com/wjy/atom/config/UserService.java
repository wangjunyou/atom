package com.wjy.atom.config;

import com.wjy.atom.config.annotation.Config;

import javax.inject.Inject;

public class UserService {

    private User user;

    private String string;

    private char aChar;

    private double aDouble;

    private float aFloat;

    private boolean aBoolean;

    private byte aByte;

    private long aLong;

    @Inject
    public UserService(
            @Config("atom.string") String string,
            @Config("atom.char") char aChar,
            @Config("atom.double") double aDouble,
            @Config("atom.float") float aFloat,
            @Config("atom.boolean") boolean aBoolean,
            @Config("atom.byte") byte aByte,
            @Config("atom.long") long aLong) {
//        this.user = user;
        this.string = string;
        this.aChar = aChar;
        this.aDouble = aDouble;
        this.aFloat = aFloat;
        this.aBoolean = aBoolean;
        this.aByte = aByte;
        this.aLong = aLong;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "string='" + string + '\'' +
                ", aChar=" + aChar +
                ", aDouble=" + aDouble +
                ", aFloat=" + aFloat +
                ", aBoolean=" + aBoolean +
                ", aByte=" + aByte +
                ", aLong=" + aLong +
                '}';
    }
}
