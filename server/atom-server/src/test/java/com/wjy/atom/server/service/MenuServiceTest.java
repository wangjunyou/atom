package com.wjy.atom.server.service;

import com.google.inject.Injector;
import com.wjy.atom.server.ServiceInject;
import com.wjy.atom.server.domain.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class MenuServiceTest {

    private MenuService menuService;

    @BeforeEach
    public void before() {
        Injector injector = ServiceInject.getInjector();
        menuService = injector.getInstance(MenuService.class);
    }

    @Test
    public void selectMenuById() {
        Menu menu = menuService.selectById(1);
        System.out.println(menu.toString());
    }

    @Test
    public void insertMenu() {
        Menu menu = new Menu();
        menu.setId(2);
        menu.setMenuName("home");
        menu.setMenuPath("/home");
        menu.setMenuComponent("home");
        menu.setMenuIcon("home");
        menu.setDisplay(1);
        menu.setOrderNum(1.0d);
        menu.setUpdateTime(new Date());
        menu.setCreateTime(new Date());
        menuService.insert(menu);
    }

    @Test
    public void updateMenu() {
        Menu menu = menuService.selectById(2);
        menu.setDisplay(2);
        menu.setUpdateTime(new Date());
        menuService.update(menu);
    }

    @Test
    public void updateMenuN() {
        Menu menu = new Menu();
        menu.setId(2);
        menu.setDisplay(1);
        menu.setUpdateTime(new Date());
        menuService.updateIfNecessary(menu);
    }

    @Test
    public void deleteMenu() {
        menuService.delete(2);
    }

    @Test
    public void selectMenuByRoleId() {
        List<Menu> menus = menuService.selectMenuByRoleId(1);
        menus.forEach(m -> {
            System.out.println(m.toString());
        });
    }
}
