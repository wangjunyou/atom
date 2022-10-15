package com.wjy.atom.server.service;

import com.wjy.atom.server.domain.User;

import java.util.List;

public interface UserService extends BaseService<User>{

    public List<User> selectUserByName(String name);

}
