package com.adi.fsmemory.uim.service.impl;


import com.adi.fsmemory.entity.uim.SysPermission;
import com.adi.fsmemory.uim.mapper.SysUserPermissionMapper;
import com.adi.fsmemory.uim.service.SysUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserPermissionServiceImpl implements SysUserPermissionService {

    @Autowired
    private SysUserPermissionMapper upMapper;

    @Override
    public List<SysPermission> getPermissionsByUserName(String userName) {
        return upMapper.queryPermissionsByUserName(userName);
    }

    @Override
    public Set<String> getPermissionStrsByUserName(String userName) {

        List<SysPermission> sysPermissions = getPermissionsByUserName(userName);

        if (sysPermissions != null && sysPermissions.size() > 0) {
            final Set<String> permissionStrs = new HashSet<>();
            sysPermissions.forEach(p -> permissionStrs.add(p.getPerName()));
            return permissionStrs;
        }

        return null;
    }
}
