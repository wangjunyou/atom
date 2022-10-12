package com.wjy.atom.server.mapper;

import com.wjy.atom.server.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> queryUserByName(String name);

    public void insertUser(User user);

    public void deleteUserById(Integer id);

    public void updateUser(User user);

    public void updateUserIfNecessary(User user);

}
