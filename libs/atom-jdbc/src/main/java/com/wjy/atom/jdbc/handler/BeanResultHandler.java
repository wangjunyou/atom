package com.wjy.atom.jdbc.handler;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BeanResultHandler implements ResultHandler<Object> {

    private String[] columns;
    private Class clazz;

    private Object obj;

    public BeanResultHandler(String[] columns, Class clazz) {
        this.columns = columns;
        this.clazz = clazz;
    }

    @Override
    public void handleResult(ResultSet resultSet) {
        try {
            obj = ConstructorUtils.invokeConstructor(clazz, null);
        } catch (NoSuchMethodException
                 | IllegalAccessException
                 | InvocationTargetException
                 | InstantiationException e) {
            throw new RuntimeException(e);
        }

        try {
            RowSetDynaClass rowSets = new RowSetDynaClass(resultSet);
            List<DynaBean> rows = rowSets.getRows();
            if (rows.size() > 0) {
                DynaBean bean = rows.get(0);
                for (String col : columns) {
                    Field field = getField(clazz, col);
                    Object value = bean.get(col);
                    FieldUtils.writeDeclaredField(obj, field.getName(), value, true);
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    public Object getResultBean() {
        return obj;
    }
}
