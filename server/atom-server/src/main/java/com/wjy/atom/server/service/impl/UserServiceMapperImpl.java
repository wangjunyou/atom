package com.wjy.atom.server.service.impl;

import com.wjy.atom.server.domain.User;
import com.wjy.atom.server.mapper.UserMapper;
import com.wjy.atom.server.service.UserService;
import org.mybatis.guice.transactional.Transactional;

import javax.inject.Inject;
import java.util.List;

public class UserServiceMapperImpl implements UserService {

    @Inject
    private UserMapper userMapper;

    @Override
    public List<User> queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }

    @Transactional
    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void updateUserIfNecessary(User user) {
        userMapper.updateUserIfNecessary(user);
    }


}
