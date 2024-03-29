package com.wjy.atom.jdbc.handler;

import com.wjy.atom.jdbc.annotation.Column;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public interface ResultHandler<T> {

    public void handleResult(ResultSet resultSet);

    default public Field getField(Class clazz, String column) {
        Field field = FieldUtils.getDeclaredField(clazz, column, true);
        if (field == null) {
            Field[] fields = FieldUtils.getAllFields(clazz);
            for (Field f : fields) {
                Column cn = f.getAnnotation(Column.class);
                if (cn == null) continue;
                if (("".equals(cn.value()) && column.equals(f.getName().toLowerCase()))
                        || (column.equals(cn.value().toLowerCase()))) {
                    return f;
                }
            }
        }
        return field;
    }
}
