package com.wjy.atom.jdbc.scripting;

public class SqlSet implements SqlNode{

    private static final String OPERATOR = "SET ";

    @Override
    public void unparse(SqlWriter writer, String leftPrec, String rightPrec) {
        writer.write(OPERATOR);
    }
}
