package com.adi.fsmemory.uim.service;

import com.adi.fsmemory.entity.uim.FunPermission;
import com.adi.fsmemory.entity.uim.FunRole;
import com.adi.fsmemory.uim.constant.FunRoleEnum;

public interface FunRolePermissionService {

    /**
     * 增加权限
     */
    boolean addFunPermission(FunPermission permission);

    /**
     * 增加角色权限
     */
    boolean addFunRolePermission(FunRoleEnum roleEnum, FunPermission permission);

}
