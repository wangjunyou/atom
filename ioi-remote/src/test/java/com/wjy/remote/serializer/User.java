package com.wjy.remote.serializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Person{

    private int id;
    private String name;
    private Date births;
    private String addr;
    private DataType dataType;

    @Override
    public void toPrint() {
        System.out.println(toString());
    }
}
