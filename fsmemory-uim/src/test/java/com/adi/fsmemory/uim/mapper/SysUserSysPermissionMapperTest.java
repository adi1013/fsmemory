package com.adi.fsmemory.uim.mapper;

import com.adi.fsmemory.entity.uim.SysPermission;
import com.adi.fsmemory.entity.uim.SysRole;
import com.adi.fsmemory.uim.utils.IdGenerator;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserSysPermissionMapperTest {

    private static Logger logger = org.apache.log4j.Logger.getLogger(SysUserMapperTest.class);

    @Autowired
    private SysUserPermissionMapper userPermissionMapper;

    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysPermissionMapper permissionMapper;


    @Test
    public void queryPermissionsByUserName() {
        userPermissionMapper.queryPermissionsByUserName("zhangsan").forEach(up -> logger.debug(up));
        logger.debug("----------------------");
        userPermissionMapper.queryPermissionsByUserName("wangwu").forEach(up -> logger.debug(up));
    }

    @Test
    public void addRolePer() {

        SysPermission sysPermission = permissionMapper.queryPermissionByPermissionName("user:add");
        SysRole sysRole = roleMapper.queryRoleByRoleName("guest");
        String rpId = IdGenerator.uuidStr();
        userPermissionMapper.addRolePer(rpId, sysRole, sysPermission);


    }
}