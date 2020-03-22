package com.adi.fsmemory.uim.service;


import com.adi.fsmemory.entity.uim.SysPermission;

import java.util.List;
import java.util.Set;

public interface SysUserPermissionService {


    List<SysPermission> getPermissionsByUserName(String userName);

    Set<String> getPermissionStrsByUserName(String userName);
}
