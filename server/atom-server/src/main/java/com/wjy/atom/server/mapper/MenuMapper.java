package com.wjy.atom.server.mapper;

import com.wjy.atom.mybatis.mapper.BaseMapper;
import com.wjy.atom.server.domain.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    public List<Menu> selectMenuByRoleId(Integer roleId);
}
