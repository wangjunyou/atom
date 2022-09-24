package com.wjy.atom.server.service;

import com.wjy.atom.server.domain.UserInfo;

public interface DeMoService {

    public void setUserInfo(UserInfo userInfo);

    public String getUserInfo();
}
