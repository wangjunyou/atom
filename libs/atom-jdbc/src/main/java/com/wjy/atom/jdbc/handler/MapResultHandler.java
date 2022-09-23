package com.wjy.atom.jdbc.handler;

import java.sql.ResultSet;
import java.util.Map;


public class MapResultHandler implements ResultHandler<Map> {

    private String[] columns;
    private Class clazz;
    private Map<String, Object> objs;

    public MapResultHandler(String[] columns, Class clazz) {
        this.columns = columns;
        this.clazz = clazz;
    }

    @Override
    public void handleResult(ResultSet resultSet) {

    }

    public Map<String, Object> getResultMap() {
        return objs;
    }
}
