package com.wjy.atom.server.service;

import com.wjy.atom.server.model.UserInfo;

public interface DeMoService {

    public void setUserInfo(UserInfo userInfo);

    public String getUserInfo();
}
