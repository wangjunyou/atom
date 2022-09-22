package com.wjy.atom.jdbc.manager;

public interface EntityManager {

    <T> T find(Class<T> clazz, Object obj, long offset, long limit);

    <T> T query(Class<T> clazz, String sql, Object... obj);

    void persist(Object obj);

//    void getTransaction();
//    void

}
