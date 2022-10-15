package com.wjy.atom.server.domain;

import lombok.Data;
import lombok.ToString;

@Data
public class User extends Base{
    private Integer id;
    private String userName;
    private String userPassword;
    private String phone;
    private String email;
}
