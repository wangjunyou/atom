package com.wjy.atom.server.domain;

import lombok.Data;

@Data
public class Role extends Base{
    private Integer id;
    private Integer parentId;
    private String roleName;
    private String description;
}
