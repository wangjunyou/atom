package com.wjy.atom.server.service;

import com.wjy.atom.server.domain.User;

import java.util.List;

public interface UserService {

    public User getUser(Integer id);

    public List<User> getUsers(String name);
}
