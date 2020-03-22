package com.adi.fsmemory.uim.service.impl;

import com.adi.fsmemory.entity.uim.FunPermission;
import com.adi.fsmemory.entity.uim.FunRole;
import com.adi.fsmemory.uim.constant.FunRoleEnum;
import com.adi.fsmemory.uim.mapper.FunPermissionMapper;
import com.adi.fsmemory.uim.mapper.FunRoleMapper;
import com.adi.fsmemory.uim.service.FunRolePermissionService;
import com.adi.fsmemory.uim.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FunRolePermissionServiceImpl implements FunRolePermissionService {

    @Autowired
    private FunPermissionMapper permissionMapper;

    @Autowired
    private FunRoleMapper roleMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addFunPermission(FunPermission permission) {
        permission.setFPermId(IdGenerator.uuidStr());
        return permissionMapper.addFunPermission(permission);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addFunRolePermission(FunRoleEnum roleEnum, FunPermission permission) {
        String rolePermId = IdGenerator.uuidStr();
        FunRole role = roleMapper.queryFunRoleByRoleName(roleEnum.getRoleName());
        permission = permissionMapper.queryFunPermByName(permission.getFPermName());
        return roleMapper.addFunRolePermission(rolePermId,role,permission);
    }
}
