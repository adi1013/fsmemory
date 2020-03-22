package com.adi.fsmemory.uim.mapper;

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
public class SysSysRoleMapperTest {

    Logger logger = Logger.getLogger(SysSysRoleMapperTest.class);

    @Autowired
    private SysRoleMapper roleMapper;

    @Test
    public void testAddRole() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(IdGenerator.uuidStr());
        sysRole.setRoleName("admin");
        sysRole.setRoleDesc("系统管理员");
        roleMapper.addRole(sysRole);

        sysRole.setRoleId(IdGenerator.uuidStr());
        sysRole.setRoleName("user");
        sysRole.setRoleDesc("系统用户");
        roleMapper.addRole(sysRole);

        sysRole.setRoleId(IdGenerator.uuidStr());
        sysRole.setRoleName("guest");
        sysRole.setRoleDesc("游客");
        roleMapper.addRole(sysRole);
    }


    @Test
    public void testQueryRoles() {
        roleMapper.queryAllRoles().forEach(r -> logger.info(r));
    }
}