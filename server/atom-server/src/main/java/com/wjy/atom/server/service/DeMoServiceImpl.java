package com.wjy.atom.server.service;

import javax.inject.Singleton;

@Singleton
public class DeMoServiceImpl implements DeMoService{

    private UserInfo userInfo;

    @Override
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getUserInfo() {
        if(userInfo==null) return "null";
        return userInfo.toString();
    }
}
