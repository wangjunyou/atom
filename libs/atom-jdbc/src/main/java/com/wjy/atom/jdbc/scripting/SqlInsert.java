package com.wjy.atom.jdbc.scripting;

public class SqlInsert implements SqlNode{

    private static final String OPERATOR = "INSERT INTO ";

    @Override
    public void unparse(SqlWriter writer, String leftPrec, String rightPrec) {
        writer.write(OPERATOR);
    }
}
