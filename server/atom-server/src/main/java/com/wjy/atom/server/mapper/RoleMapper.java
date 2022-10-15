package com.wjy.atom.server.mapper;


import com.wjy.atom.mybatis.mapper.BaseMapper;
import com.wjy.atom.server.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    public List<Role> selectRoleByUserId(Integer userId);

}
