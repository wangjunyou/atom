package com.wjy.atom.server.domain;

import lombok.Data;

@Data
public class Menu extends Base{
    private Integer id;
    private Integer parentId;
    private String menuName;
    private String menuPath;
    private String menuComponent;
    private String menuIcon;
    private Integer display;
    private Double orderNum;
}
