package com.adi.fsmemory.uim.service;

import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.constant.RoleSettingStatus;

public interface SysUserService {


    User getUser(String userName);

    User getUser(String userName, RoleSettingStatus status);

    User getAdmin(String userName);

    User getAdmin(String userName, RoleSettingStatus status);

    boolean addUser(User user);
}
