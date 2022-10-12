package com.wjy.atom.server.service;

import com.wjy.atom.server.domain.User;

import java.util.List;

public interface UserService {

    public List<User> queryUserByName(String name);

    public void insertUser(User user);

    public void deleteUserById(Integer id);

    public void updateUser(User user);

    public void updateUserIfNecessary(User user);
}
