package com.wjy.atom.jdbc.handler;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListResultHandler implements ResultHandler<List> {

    private String[] columns;
    private Class clazz;
    private List<Object> objs;

    public ListResultHandler(String[] columns, Class clazz) {
        this.columns = columns;
        this.clazz = clazz;
    }

    @Override
    public void handleResult(ResultSet resultSet) {
        try {
            RowSetDynaClass rowSet = new RowSetDynaClass(resultSet);
            List<DynaBean> rows = rowSet.getRows();
            for (DynaBean bean : rows) {
                if (objs == null) objs = new ArrayList<>();
                try {
                    Object obj = ConstructorUtils.invokeConstructor(clazz, null);
                    for (String col : columns) {
                        Field field = getField(clazz, col);
                        Object value = bean.get(col);
                        FieldUtils.writeDeclaredField(obj, field.getName(), value, true);
                    }
                    objs.add(obj);
                } catch (NoSuchMethodException
                         | IllegalAccessException
                         | InvocationTargetException
                         | InstantiationException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Object> getResultList() {
        return objs;
    }
}
