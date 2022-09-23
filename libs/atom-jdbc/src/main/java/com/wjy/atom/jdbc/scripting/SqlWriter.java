package com.wjy.atom.jdbc.scripting;

public class SqlWriter {

    private StringBuilder sb;

    private SqlWriter(){
        sb = new StringBuilder();
    }


    private SqlWriter newInstance() {
        return new SqlWriter();
    }

    public void write(String operator) {
        sb.append(operator);
    }

    public String getSql() {
        return sb.toString();
    }
}
