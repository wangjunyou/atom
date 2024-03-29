package com.wjy.atom.server.mapper;

import com.wjy.atom.mybatis.mapper.BaseMapper;
import com.wjy.atom.server.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    public List<User> selectByName(String name);


}
