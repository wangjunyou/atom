package com.wjy.atom.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String userName;
    private String userPassword;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;

}
