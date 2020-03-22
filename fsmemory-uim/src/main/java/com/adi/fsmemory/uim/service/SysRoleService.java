package com.adi.fsmemory.uim.service;

import com.adi.fsmemory.entity.uim.SysRole;
import com.adi.fsmemory.uim.constant.RoleEnum;

public interface SysRoleService {

    SysRole getRole(RoleEnum roleEnum);
}
