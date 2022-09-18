package com.wjy.atom.remote.serializer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sdata {

    private Person person;

    public void sOut() {
        person.toPrint();
    }

}
