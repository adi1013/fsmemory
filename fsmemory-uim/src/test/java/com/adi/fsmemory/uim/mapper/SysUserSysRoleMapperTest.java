package com.adi.fsmemory.uim.mapper;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserSysRoleMapperTest {

    private static Logger logger = org.apache.log4j.Logger.getLogger(SysUserMapperTest.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Test
    public void addUserRole() {

    }

    @Test
    public void queryUserRolesByUserName() {
//        userRoleMapper.queryUserRolesByUserName("zhangsan").forEach(r -> logger.info(r));
    }
}