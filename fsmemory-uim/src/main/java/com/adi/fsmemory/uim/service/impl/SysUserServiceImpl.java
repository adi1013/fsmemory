package com.adi.fsmemory.uim.service.impl;

import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.auth.exception.WaitForCheckException;
import com.adi.fsmemory.uim.constant.RoleEnum;
import com.adi.fsmemory.uim.constant.RoleSettingStatus;
import com.adi.fsmemory.uim.mapper.SysUserMapper;
import com.adi.fsmemory.uim.service.SysUserService;
import com.adi.fsmemory.uim.utils.IdGenerator;
import com.adi.fsmemory.uim.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;


    @Override
    public User getUser(String userName) {
        User user = userMapper.queryUserByUserName(userName, RoleEnum.USER.getRoleName(), RoleSettingStatus.SETTING.getStatus());
        return user;
    }


    @Override
    public User getAdmin(String userName) {
        User user = userMapper.queryUserByUserName(userName, RoleEnum.ADMIN.getRoleName(),
                RoleSettingStatus.SETTING.getStatus());
        //如果找不到可用的管理员，则判断其是否处于设置申请的状态
        if (user == null) {
            user = userMapper.queryUserByUserName(userName, RoleEnum.ADMIN.getRoleName(),
                    RoleSettingStatus.SETTING_APPLY.getStatus());
            if (user != null) {
                throw new WaitForCheckException("待审核");
            }
        }
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized boolean addUser(User user) {
        boolean result = false;

        if (user.getPassword() != null && user.getUserName() != null) {
            String userId = IdGenerator.uuidStr();
            String salt = String.valueOf(new Date().getTime());
            user.setUserId(userId)
                .setSalt(salt)
                .setPassword(MD5Utils.md5Str(user.getPassword(), salt));
            result = userMapper.addUser(user);
        }

        return result;
    }

    @Override
    public User getUser(String userName, RoleSettingStatus status) {
        User user = this.getUserByRoleAndStatus(userName, RoleEnum.USER, status);
        return user;
    }

    @Override
    public User getAdmin(String userName, RoleSettingStatus status) {
        User admin = this.getUserByRoleAndStatus(userName, RoleEnum.ADMIN, status);
        return admin;
    }

    private User getUserByRoleAndStatus(String userName, RoleEnum roleEnum, RoleSettingStatus status) {
        return userMapper.queryUserByUserName(userName, roleEnum.getRoleName(),
                status.getStatus());
    }
}
