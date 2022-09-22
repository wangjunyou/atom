package com.wjy.atom.jdbc.manager.impl;

import com.wjy.atom.jdbc.annotation.Column;
import com.wjy.atom.jdbc.manager.EntityManager;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityManagerImpl implements EntityManager {

    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerImpl.class);

    private static final QueryRunner qRunner = new QueryRunner();

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
        String[] cName = getColumnName(obj);
        StringBuilder cnsb = new StringBuilder();
        for (String cn : cName) {
            if(cnsb.length() ==0) cnsb.append(cn);
            cnsb.append(","+cn);
        }
        Map<String, Object> params = getParams(obj);

//        qRunner.query(connection, )
        return null;
    }

    @Override
    public <T> T query(Class<T> clazz, String sql, Object... obj) {
        return null;
    }

    @Override
    public void persist(Object obj) {

    }

    private Map<String, Object> getParams(Object obj) {

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> datas = new HashMap<>();
        for (Field field : fields) {
            Object data = null;
            try {
                data = field.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (data != null) {
                Column column = field.getDeclaredAnnotation(Column.class);
                if (column == null) continue;
                String cname = column.name();
                String key = cname.equals("") ? field.getName() : cname;
                datas.put(key, data);
            }
        }
        return datas.size() > 0 ? datas : null;
    }

    private String[] getColumnName(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String[] columns = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getDeclaredAnnotation(Column.class);
            String cName = column.name();
            columns[i] = cName.equals("") ? fields[i].getName() : cName;
        }
        return columns;
    }
}
