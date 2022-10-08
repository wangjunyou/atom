package com.wjy.atom.mybatis.sample.mapper;

import com.wjy.atom.mybatis.sample.domain.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    User getUser(Integer id);
}
