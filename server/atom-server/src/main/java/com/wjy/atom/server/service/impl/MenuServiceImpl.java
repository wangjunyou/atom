package com.wjy.atom.server.service.impl;

import com.wjy.atom.server.domain.Menu;
import com.wjy.atom.server.mapper.MenuMapper;
import com.wjy.atom.server.service.MenuService;

import javax.inject.Inject;
import java.util.List;

public class MenuServiceImpl implements MenuService {

    @Inject
    private MenuMapper menuMapper;

    @Override
    public Menu selectById(Integer id) {
        return menuMapper.selectById(id);
    }

    @Override
    public void insert(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.update(menu);
    }

    @Override
    public void updateIfNecessary(Menu menu) {
        menuMapper.updateIfNecessary(menu);
    }

    @Override
    public void delete(Integer id) {
        menuMapper.delete(id);
    }

    @Override
    public List<Menu> selectMenuByRoleId(Integer roleId) {
        return menuMapper.selectMenuByRoleId(roleId);
    }
}
