package com.wjy.atom.trino;

import io.trino.jdbc.TrinoDriver;

import java.sql.*;
import java.util.Properties;

public class HiveMain {
    public static void main(String[] args) throws SQLException {
        TrinoDriver trinoDriver = new TrinoDriver();
        Properties properties = new Properties();

        properties.setProperty("user", "hadoop");
        properties.setProperty("password", "");
        Connection connection = trinoDriver.connect("jdbc:trino://node1:8880/hive/ods_stream", properties);

//        PreparedStatement statement = connection.prepareStatement("select * from test_txt where c1='CMELDBCBHGGLFNJHIKGFMCN '");
        PreparedStatement statement = connection.prepareStatement("select * from test_txt");
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            String columnName = metaData.getColumnName(i + 1);
            String columnClassName = metaData.getColumnClassName(i + 1);
            String columnTypeName = metaData.getColumnTypeName(i + 1);
            System.out.println(columnClassName + "=" + columnName + "=" + columnTypeName);
        }
        System.out.println("===============");
        while (resultSet.next()) {
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object object = resultSet.getObject(columnName);
                System.out.print(columnName + "=" + object + ",");
            }
            System.out.println();
        }
    }

}
