package com.wjy.atom.jdbc.handler;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class ListMapResultHandler implements ResultHandler<List<Map<String, Object>>> {

    private String[] columns;
    private Class clazz;
    private List<Map<String, Object>> objs;

    @Override
    public void handleResult(ResultSet resultSet) {

    }

    public List<Map<String, Object>> getResultListMap() {
        return objs;
    }


}
