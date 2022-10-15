package com.wjy.atom.server.service.impl;

import com.wjy.atom.server.domain.Role;
import com.wjy.atom.server.mapper.RoleMapper;
import com.wjy.atom.server.service.RoleService;

import javax.inject.Inject;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Inject
    private RoleMapper roleMapper;

    @Override
    public Role selectById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public void insert(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void update(Role role) {
        roleMapper.update(role);
    }

    @Override
    public void updateIfNecessary(Role role) {
        roleMapper.updateIfNecessary(role);
    }

    @Override
    public void delete(Integer id) {
        roleMapper.delete(id);
    }

    @Override
    public List<Role> selectRoleByUserId(Integer userId) {
        return roleMapper.selectRoleByUserId(userId);
    }
}
