package com.wjy.atom.jdbc.handler;


import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanResultHandler implements ResultHandler<Object> {

    private String[] columns;
    private Class clazz;

    private Object obj;

    @Override
    public void handleResult(ResultSet resultSet) {
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                if (!resultSet.next()) break;
                for (String column : columns) {
                    Field field = getField(clazz, column);
                    Object value = resultSet.getObject(column);
                    FieldUtils.writeField(obj, field.getName(), value);
                }
            } catch (SQLException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public Object getResultBean() {
        return obj;
    }
}
