package com.wjy.atom.jdbc.scripting;

public class SqlUpdate implements SqlNode{

    private static final String OPERATOR = "UPDATE ";

    @Override
    public void unparse(SqlWriter writer, String leftPrec, String rightPrec) {
        writer.write(OPERATOR);
    }
}
