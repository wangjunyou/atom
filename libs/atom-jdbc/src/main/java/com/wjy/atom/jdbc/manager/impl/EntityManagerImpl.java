package com.wjy.atom.jdbc.manager.impl;

import com.wjy.atom.jdbc.annotation.Column;
import com.wjy.atom.jdbc.annotation.Table;
import com.wjy.atom.jdbc.manager.EntityManager;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
    public <T> T find(Class<T> clazz, Object obj, long offset, long limit) {
        String sql = "SELECT %s FROM %s WHERE %s LIMIT " + limit + " OFFSET " + offset;
        String tableName = getTableName(obj);
        String[] cName = getColumnName(obj);
        StringBuilder cnsb = new StringBuilder();
        for (String cn : cName) {
            if (cnsb.length() == 0) cnsb.append(cn);
            else cnsb.append("," + cn);
        }
        Map<String, Object> params = getParams(obj);
        if (params == null) return null;
        StringBuilder wsb = new StringBuilder();
        params.forEach((k, v) -> {
            if (wsb.length() == 0) wsb.append(k + "=?");
            else wsb.append("," + k + "=?");
        });
        sql = String.format(sql, cnsb.toString(), tableName, wsb.toString());

//        qRunner.query(connection, sql, )
        return null;
    }

    @Override
    public <T> T query(Class<T> clazz, String sql, Object... obj) {
        return null;
    }

    @Override
    public void persist(Object obj) {

    }


    private String getTableName(Object obj) {
        Class<?> clazz = obj.getClass();
        Table table = clazz.getDeclaredAnnotation(Table.class);
        if (table == null) return clazz.getSimpleName();
        return table.name();
    }

    private Map<String, Object> getParams(Object obj) {

        Field[] fields = FieldUtils.getAllFields(obj.getClass());
        Map<String, Object> datas = new HashMap<>();
        for (Field field : fields) {
            Object data = null;
            try {
                data = field.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (data != null) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) continue;
                String cname = column.name();
                String key = cname.equals("") ? field.getName() : cname;
                datas.put(key, data);
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
            String cName = column.name();
            columns[i] = cName.equals("") ? fields[i].getName() : cName;
        }
        return columns;
    }
}
