package com.wjy.atom.jdbc.scripting;

public interface SqlNode {

    public void unparse(SqlWriter writer, String leftPrec, String rightPrec);
}
