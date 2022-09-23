package com.wjy.atom.jdbc.scripting;

import org.apache.commons.lang3.StringUtils;

public class SqlColumns implements SqlNode{

    private static final String OPERATOR = " ";

    private String[] columns;

    public SqlColumns(String[] columns) {
        this.columns = columns;
    }

    @Override
    public void unparse(SqlWriter writer, String leftPrec, String rightPrec) {
        for (int i = 0; i < columns.length; i++) {
            if(i==0){
                writer.write(columns[i]);
                continue;
            }
            if(StringUtils.isNotEmpty(leftPrec))
                writer.write(leftPrec);
            writer.write(columns[i]);
            if(StringUtils.isNotEmpty(rightPrec))
                writer.write(rightPrec);
        }
        writer.write(OPERATOR);

    }
}
