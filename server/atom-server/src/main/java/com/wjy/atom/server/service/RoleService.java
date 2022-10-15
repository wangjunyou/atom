package com.wjy.atom.server.service;

import com.wjy.atom.server.domain.Role;

import java.util.List;

public interface RoleService extends BaseService<Role> {

    public List<Role> selectRoleByUserId(Integer userId);

}
