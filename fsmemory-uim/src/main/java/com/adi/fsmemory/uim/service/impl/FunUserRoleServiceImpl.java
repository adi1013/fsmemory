package com.adi.fsmemory.uim.service.impl;

import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.FunRole;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.constant.FunRoleEnum;
import com.adi.fsmemory.uim.constant.FunUsefulFlag;
import com.adi.fsmemory.uim.constant.RoleEnum;
import com.adi.fsmemory.uim.constant.RoleSettingStatus;
import com.adi.fsmemory.uim.mapper.FunGroupMapper;
import com.adi.fsmemory.uim.mapper.FunRoleMapper;
import com.adi.fsmemory.uim.mapper.FunUserGroupMapper;
import com.adi.fsmemory.uim.mapper.SysUserMapper;
import com.adi.fsmemory.uim.service.FunUserRoleService;
import com.adi.fsmemory.uim.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FunUserRoleServiceImpl implements FunUserRoleService {

    @Autowired
    private FunRoleMapper roleMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private FunGroupMapper groupMapper;

    @Autowired
    private FunUserGroupMapper userGroupMapper;
    @Override
    public boolean addFunRole(FunRole funRole) {
        funRole.setFRoleId(IdGenerator.uuidStr());
        return roleMapper.addFunRole(funRole);
    }

    @Override
    @Transactional
    public boolean addOrRemoveFunUserRole(User user, FunRoleEnum funRoleEnum, FunGroup group, FunUsefulFlag flag) {
        //获取user和role以及group

        user = userMapper.queryUserByUserName(user.getUserName(),
                RoleEnum.USER.getRoleName(), RoleSettingStatus.SETTING.getStatus());
        FunRole role = roleMapper.queryFunRoleByRoleName(funRoleEnum.getRoleName());
        group = groupMapper.getGroup(group.getSearchId());
        String userRoleId = this.managerExists(group.getGroupId(), user.getUserId(), role.getFRoleId());

        //判断是否存在

        if (userRoleId == null) { //如果不存在的话，说明此时是附加角色操作，则直接新增
            String fUserRoleId = IdGenerator.uuidStr();
            return roleMapper.addFunUserRole(fUserRoleId, user, role, group, flag.getUse());
        } else {
            //如果存在的话则将其重置为目标状态
            return roleMapper.updUserRoleUseByURId(flag.getUse(), userRoleId);
        }
    }

    private String managerExists(String groupId, String userId, String fRoleId) {
        return roleMapper.queryUserRoleIdUnique(groupId, userId, fRoleId);
    }


    @Override
    @Transactional
    public boolean removeMember(String userName, String searchId) {
        //更新所有  fun_user_role中的 flag
        roleMapper.updUserRoleFlag(userName, searchId, FunUsefulFlag.USE.getUse());
        //删除fun_user_group记录
        userGroupMapper.removeMember(userName, searchId);
        return true;
    }
}
