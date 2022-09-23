package com.wjy.atom.jdbc.manager.impl;

import com.wjy.atom.jdbc.annotation.Column;
import com.wjy.atom.jdbc.annotation.Table;
import com.wjy.atom.jdbc.handler.BeanResultHandler;
import com.wjy.atom.jdbc.handler.ListResultHandler;
import com.wjy.atom.jdbc.manager.EntityManager;
import com.wjy.atom.jdbc.manager.QueryRunner;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityManagerImpl implements EntityManager {

    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerImpl.class);

    private Connection connection;

    @Inject
    public EntityManagerImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("Get Connection faild. {}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T selectOne(Class<T> clazz, Object obj) {
        String sql = QueryRunner.SELECT_TEMP;

        String tableName = getTableName(obj);
        String[] cName = getColumnName(obj);

        String cJoin = columnJoin(cName);

        Map<String, Object> params = getParams(obj);
        if (params == null) return null;
        StringBuilder wsb = new StringBuilder();
        Object[] objects = new Object[params.size()];
        AtomicInteger i = new AtomicInteger(0);
        params.forEach((k, v) -> {
            if (wsb.length() == 0) wsb.append(k + "=?");
            else wsb.append("," + k + "=?");
            objects[i.getAndIncrement()] = v;
        });

        sql = String.format(sql, cJoin, tableName, wsb.toString());

        ResultSet resultSet = QueryRunner.getExecuteQuery(connection, sql, clazz, objects);
        BeanResultHandler resultHandler = new BeanResultHandler(cName, clazz);
        resultHandler.handleResult(resultSet);
        return (T) resultHandler.getResultBean();
    }

    @Override
    public <T> List<T> selectList(Class<T> clazz, Object obj) {
        return selectList(clazz, obj, 0, 10);
    }

    @Override
    public <T> List<T> selectList(Class<T> clazz, Object obj, long offset, long limit) {
        String lmt = String.format(QueryRunner.LIMIT_TEMP, limit, offset);
        String sql = QueryRunner.SELECT_TEMP + " " + lmt;
        String tableName = getTableName(obj);
        String[] cName = getColumnName(obj);
        String cJoin = columnJoin(cName);

        Map<String, Object> params = getParams(obj);
        if (params == null) return null;
        StringBuilder wsb = new StringBuilder();
        Object[] objects = new Object[params.size()];
        AtomicInteger i = new AtomicInteger(0);
        params.forEach((k, v) -> {
            if (wsb.length() == 0) wsb.append(k + "=?");
            else wsb.append(" and " + k + "=?");
            objects[i.getAndIncrement()] = v;
        });

        sql = String.format(sql, cJoin, tableName, wsb.toString());

        ResultSet resultSet = QueryRunner.getExecuteQuery(connection, sql, clazz, objects);
        ListResultHandler resultHandler = new ListResultHandler(cName, clazz);
        resultHandler.handleResult(resultSet);
        return (List<T>) resultHandler.getResultList();

    }

    @Override
    public void persist(Object obj) {

    }

    private String columnJoin(String[] columns) {

        StringBuilder cnsb = new StringBuilder();
        for (String cn : columns) {
            if (cnsb.length() == 0) cnsb.append(cn);
            else cnsb.append("," + cn);
        }
        return cnsb.toString();

    }


    private String getTableName(Object obj) {
        Class<?> clazz = obj.getClass();
        Table table = clazz.getDeclaredAnnotation(Table.class);
        if (table == null) return clazz.getSimpleName();
        return table.value();
    }

    private Map<String, Object> getParams(Object obj) {

        Field[] fields = FieldUtils.getAllFields(obj.getClass());
        Map<String, Object> datas = new HashMap<>();
        for (Field field : fields) {
            Object data = null;
            try {
                data = FieldUtils.readDeclaredField(obj, field.getName(), true);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (data != null) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) continue;
                if ("".equals(column.value()))
                    datas.put(field.getName().toLowerCase(), data);
                else
                    datas.put(column.value().toLowerCase(), data);
            }
        }
        return datas.size() > 0 ? datas : null;
    }

    private String[] getColumnName(Object obj) {
        Field[] fields = FieldUtils.getAllFields(obj.getClass());
        String[] columns = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if (column == null) continue;
            if ("".equals(column.value()))
                columns[i] = fields[i].getName().toLowerCase();
            else
                columns[i] = column.value().toLowerCase();

        }
        return columns;
    }
}
