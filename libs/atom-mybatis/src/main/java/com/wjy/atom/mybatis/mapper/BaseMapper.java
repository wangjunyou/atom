package com.wjy.atom.mybatis.mapper;

public interface BaseMapper<T> {

    public T selectById(Integer id);

    public void insert(T t);

    public void update(T t);

    public void updateIfNecessary(T t);

    public void delete(Integer id);

}
