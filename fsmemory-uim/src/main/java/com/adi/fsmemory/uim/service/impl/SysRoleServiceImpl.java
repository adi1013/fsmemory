package com.adi.fsmemory.uim.service.impl;

import com.adi.fsmemory.entity.uim.SysRole;
import com.adi.fsmemory.uim.mapper.SysRoleMapper;
import com.adi.fsmemory.uim.constant.RoleEnum;
import com.adi.fsmemory.uim.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public SysRole getRole(RoleEnum roleEnum) {

        return roleMapper.queryRoleByRoleName(roleEnum.getRoleName());

    }
}
