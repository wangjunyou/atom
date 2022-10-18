package com.wjy.atom.server.service;

import com.google.inject.Injector;
import com.wjy.atom.server.ServiceInject;
import com.wjy.atom.server.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class RoleServiceTest {

    private RoleService roleService;

    @BeforeEach
    public void before() {
        Injector injector = ServiceInject.getInjector();
        roleService = injector.getInstance(RoleService.class);
    }

    @Test
    public void selectRoleById() {
        Role role = roleService.selectById(2);
        System.out.println(role.toString());
    }

    @Test
    public void insertRole() {
        Role role = new Role();
        role.setId(2);
        role.setRoleName("hadoop");
        role.setDescription("hadoop role");
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        roleService.insert(role);
    }

    @Test
    public void updateRole() {
        Role role = roleService.selectById(2);
        role.setDescription("hadoop edit role");
        role.setUpdateTime(new Date());
        roleService.update(role);
    }

    @Test
    public void updateRoleN() {
        Role role = new Role();
        role.setId(2);
        role.setDescription("hadoop role");
        role.setUpdateTime(new Date());
        roleService.updateIfNecessary(role);
    }

    @Test
    public void deleteRole() {
        roleService.delete(2);
    }

    @Test
    public void selectRoleByUserId() {
        List<Role> roles = roleService.selectRoleByUserId(1);
        roles.forEach(u -> System.out.println(u.toString()));
    }
}
