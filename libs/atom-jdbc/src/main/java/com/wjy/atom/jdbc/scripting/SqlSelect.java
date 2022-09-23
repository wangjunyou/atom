package com.wjy.atom.jdbc.scripting;

public class SqlSelect implements SqlNode{

    private static final String OPERATOR = "SELECT ";

    @Override
    public void unparse(SqlWriter writer, String leftPrec, String rightPrec) {
        writer.write(OPERATOR);
    }
}
