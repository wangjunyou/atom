package com.wjy.atom.jdbc.manager;

import java.util.List;

public interface EntityManager {

    public <T> T selectOne(Class<T> clazz, Object obj);

    public <T> List<T> selectList(Class<T> clazz, Object obj);
    public <T> List<T> selectList(Class<T> clazz, Object obj, long offset, long limit);

    public void persist(Object obj);

//    void getTransaction();
//    void

}
