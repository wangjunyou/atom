package com.wjy.atom.mybatis.sample.service;

import com.wjy.atom.mybatis.sample.domain.User;
import com.wjy.atom.mybatis.sample.mapper.UserMapper;

import javax.inject.Inject;

public class UserServiceImpl implements UserService{

    @Inject
    private UserMapper userMapper;

    @Override
    public User getUser(Integer id) {
        return userMapper.getUser(id);
    }
}
