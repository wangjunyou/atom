package com.wjy.atom.server.service;

import com.wjy.atom.server.domain.Menu;

import java.util.List;

public interface MenuService extends BaseService<Menu> {

    public List<Menu> selectMenuByRoleId(Integer roleId);
}
