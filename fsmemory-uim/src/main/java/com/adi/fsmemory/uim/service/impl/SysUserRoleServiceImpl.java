package com.adi.fsmemory.uim.service.impl;


import com.adi.fsmemory.entity.uim.SysRole;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.mapper.SysUserRoleMapper;
import com.adi.fsmemory.uim.constant.RoleSettingStatus;
import com.adi.fsmemory.uim.service.SysUserRoleService;
import com.adi.fsmemory.uim.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;



    @Override
    public List<SysRole> getRolesByUserName(String userName) {
        return userRoleMapper.queryUserRolesByUserName(userName);
    }

    @Override
    public Set<String> getRoleStrsByUserName(String userName) {
        List<SysRole> sysRoles = getRolesByUserName(userName);

        if (sysRoles != null && sysRoles.size() > 0) {
            final Set<String> rolesStr = new HashSet<>();
            sysRoles.forEach(r -> rolesStr.add(r.getRoleName()));
            return rolesStr;
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
    public boolean addSysRole(User user, SysRole sysRole, RoleSettingStatus roleSettingStatus) {
        return this.addRole(user, sysRole, roleSettingStatus.getStatus());
    }

    @Override
    @Transactional
    public boolean updUserRoleFlag(User user, SysRole sysRole, RoleSettingStatus roleSettingStatus) {
        return userRoleMapper.updUserRoleFlag(user, sysRole, roleSettingStatus.getStatus());
    }

    private boolean addRole(User user, SysRole sysRole, Integer flag) {
        String urId = IdGenerator.uuidStr();
        return userRoleMapper.addUserRole(urId, user, sysRole, flag);
    }
}
