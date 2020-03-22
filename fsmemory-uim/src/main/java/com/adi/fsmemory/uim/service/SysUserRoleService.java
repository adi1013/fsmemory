package com.adi.fsmemory.uim.service;


import com.adi.fsmemory.entity.uim.SysRole;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.constant.RoleSettingStatus;

import java.util.List;
import java.util.Set;


public interface SysUserRoleService {

    List<SysRole> getRolesByUserName(String userName);

    Set<String> getRoleStrsByUserName(String userName);

    boolean addSysRole(User user, SysRole sysRole, RoleSettingStatus roleSettingStatus);

    boolean updUserRoleFlag(User user, SysRole sysRole, RoleSettingStatus roleSettingStatus);

}
