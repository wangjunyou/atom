package com.wjy.atom.jdbc.manager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryRunner {

    /**
     * select id from user where id=1 limit 10 offset 0
     */
    public static final String SELECT_TEMP = "select %s from %s where %s";
    public static final String INSERT_TEMP = "insert into %S (%s) values(%s)";
    public static final String UPDATE_TEMP = "update %s set %s where %S";
    public static final String DELETE_TEMP = "delete from %S where %s";
    public static final String LIMIT_TEMP = "limit %s offset %s";

    public static ResultSet getExecuteQuery(Connection conn, String sql, Class clazz, Object... obj) {
        try {
            PreparedStatement statement = execute(conn, sql, clazz, obj);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static int executeUpdate(Connection conn, String sql, Class clazz, Object... obj) {
        PreparedStatement statement = execute(conn, sql, clazz, obj);
        try {
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement execute(Connection conn, String sql, Class clazz, Object... obj) {
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                statement.setObject(i + 1, obj[i]);
            }
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String limitTemp = QueryRunner.LIMIT_TEMP;
        long limit = 10;
        long offset = 0;
        String format = String.format(limitTemp, limit, offset);
        System.out.println(format);
    }


}
