package com.wjy.atom.server.mapper;

import com.wjy.atom.server.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public User getUser(Integer id);

    public List<User> getUsers(String name);
}
