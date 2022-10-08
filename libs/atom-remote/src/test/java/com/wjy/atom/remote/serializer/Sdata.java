package com.wjy.atom.remote.serializer;



public class Sdata {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void sOut() {
        person.toPrint();
    }

}
