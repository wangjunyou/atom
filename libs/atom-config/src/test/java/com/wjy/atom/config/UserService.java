package com.wjy.atom.config;

import com.wjy.atom.config.annotation.Config;

import javax.inject.Inject;

public class UserService {

    @Inject
    @Config("atom.string")
    private String string;
    @Inject
    @Config("atom.char")
    private char aChar;
    @Inject
    @Config("atom.double")
    private double aDouble;
    @Inject
    @Config("atom.float")
    private float aFloat;
    @Inject
    @Config("atom.boolean")
    private boolean aBoolean;
    @Inject
    @Config("atom.byte")
    private byte aByte;
    @Inject
    @Config("atom.long")
    private long aLong;

    /*@Inject
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
    }*/

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
