package com.adi.fsmemory.uim.mapper;

import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.utils.IdGenerator;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper userMapper;

    private static Logger logger = org.apache.log4j.Logger.getLogger(SysUserMapperTest.class);

    @Test
    public void addUser() {
        String salt = String.valueOf(new Date().getTime());
        int iterations = 10;
        String password = "123456";
        Md5Hash md5Hash = new Md5Hash(password,salt,iterations);
        User user = new User();
        user.setUserId(IdGenerator.uuidStr());
        user.setTel("15914768177");
        user.setSalt(salt);
        user.setPassword(md5Hash.toString());
        userMapper.addUser(user);
    }

    @Test
    public void queryUsers() {
        userMapper.queryUsers().forEach(u -> logger.info(u));
    }
}