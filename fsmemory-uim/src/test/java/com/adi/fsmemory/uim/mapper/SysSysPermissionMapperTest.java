package com.adi.fsmemory.uim.mapper;

import com.adi.fsmemory.entity.uim.SysPermission;
import com.adi.fsmemory.uim.utils.IdGenerator;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SysSysPermissionMapperTest {

    Logger logger = Logger.getLogger(SysSysPermissionMapperTest.class);

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Test
    public void testAddPermission() {

        SysPermission sysPermission = new SysPermission();
//        sysPermission.setPerId(IdGenerator.uuidStr());
//        sysPermission.setPerName("role_premission:add");
//        sysPermission.setPerDesc("给角色增加权限");
//        permissionMapper.addPermission(sysPermission);
//
//        sysPermission.setPerId(IdGenerator.uuidStr());
//        sysPermission.setPerName("role_premission:delete");
//        sysPermission.setPerDesc("给角色去除权限");
//        permissionMapper.addPermission(sysPermission);

//        sysPermission.setPerId(IdGenerator.uuidStr());
//        sysPermission.setPerName("user_role:update");
//        sysPermission.setPerDesc("更新权限操作");
//        permissionMapper.addPermission(sysPermission);
//
//        sysPermission.setPerId(IdGenerator.uuidStr());
//        sysPermission.setPerName("sysPermission:query");
//        sysPermission.setPerDesc("查找权限操作");
//        permissionMapper.addPermission(sysPermission);

//        sysPermission.setPerId(IdGenerator.uuidStr());
//        sysPermission.setPerName("role_premission:*");
//        sysPermission.setPerDesc("有关用户角色权限指派的所有权限");
//        permissionMapper.addPermission(sysPermission);

        sysPermission.setPerId(IdGenerator.uuidStr());
        sysPermission.setPerName("*:*");
        sysPermission.setPerDesc("所有资源的操作权限");
        permissionMapper.addPermission(sysPermission);
    }

    @Test
    public void testQueryAllPermissions() {
        permissionMapper.queryAllPermissions().forEach(p -> logger.info(p));
    }
}