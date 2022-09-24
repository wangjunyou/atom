package com.wjy.atom.server.service.impl;

import com.wjy.atom.server.domain.User;
import com.wjy.atom.server.mapper.UserMapper;
import com.wjy.atom.server.service.UserService;

import javax.inject.Inject;

public class UserServiceMapperImpl implements UserService {

    @Inject
    private UserMapper userMapper;

    @Override
    public User getUser(Integer id) {
        return userMapper.getUser(id);
    }
}
