package com.wjy.atom.jdbc.scripting;

public class SqlDelete implements SqlNode{

    private static final String OPERATOR = "DELETE FROM ";

    @Override
    public void unparse(SqlWriter writer, String leftPrec, String rightPrec) {
        writer.write(OPERATOR);
    }
}
