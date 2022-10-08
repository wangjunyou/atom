package com.wjy.atom.remote.serializer;


import java.util.Date;

public class User implements Person{

    private int id;
    private String name;
    private Date births;
    private String addr;
    private DataType dataType;

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public void toPrint() {
        System.out.println(toString());
    }
}
