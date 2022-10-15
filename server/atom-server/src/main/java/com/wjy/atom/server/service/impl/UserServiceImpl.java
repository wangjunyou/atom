package com.wjy.atom.server.service.impl;

import com.wjy.atom.server.domain.User;
import com.wjy.atom.server.mapper.UserMapper;
import com.wjy.atom.server.service.UserService;

import javax.inject.Inject;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Inject
    private UserMapper userMapper;

    @Override
    public List<User> selectUserByName(String name) {
        return userMapper.selectByName(name);
    }


    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void updateIfNecessary(User user) {

    }

    @Override
    public void delete(Integer id) {

    }
}
