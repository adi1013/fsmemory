package com.adi.fsmemory.uim.service.impl;

import com.adi.fsmemory.entity.uim.FunMemberGroup;
import com.adi.fsmemory.entity.uim.FunRole;
import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.auth.exception.SearchIdRepetitionException;
import com.adi.fsmemory.uim.constant.*;
import com.adi.fsmemory.uim.mapper.FunGroupMapper;
import com.adi.fsmemory.uim.mapper.FunRoleMapper;
import com.adi.fsmemory.uim.mapper.FunUserGroupMapper;
import com.adi.fsmemory.uim.mapper.SysUserMapper;
import com.adi.fsmemory.uim.service.FunGroupService;
import com.adi.fsmemory.uim.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FunGroupServiceImpl implements FunGroupService {

    @Autowired
    private FunGroupMapper groupMapper;

    @Autowired
    private FunRoleMapper roleMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private FunUserGroupMapper userGroupMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addGroup(FunGroup group, User creator) {
        List<String> searchIds = groupMapper.queryAllSearchId();

        group.setGroupId(IdGenerator.uuidStr())
             .setUserId(creator.getUserId())
             .setGroupId(IdGenerator.uuidStr());

        if (group.getSearchId() == null) {
            group.setSearchId(IdGenerator.randomSearchId(searchIds));
        } else {
            if (searchIds.contains(group.getSearchId())) {
                throw new SearchIdRepetitionException("组织编号重复");
            }
        }
        //userGroup也要增加群主记录

        String fUserRoleId = IdGenerator.uuidStr();
        FunRole role = roleMapper.queryFunRoleByRoleName(FunRoleEnum.CREATOR.getRoleName());
        //插入组织
        groupMapper.addGroup(group);
        //插入组织成员表
        userGroupMapper.addMember(IdGenerator.uuidStr(),group,creator,FunUsefulFlag.USE.getUse());
        //角色为组织创建者
        roleMapper.addFunUserRole(fUserRoleId, creator, role, group,FunUsefulFlag.USE.getUse());
        return true;
    }

    @Override
    public List<FunGroup> queryGroupBySearchId(String searchId) {
        return null;
    }

    @Override
    public List<FunGroup> queryGroupByUserName(String userName) {
        return null;
    }

    @Override
    public boolean abortGroup(FunGroup group) {
        return false;
    }

    @Override
    @Transactional
    public boolean joinApply(FunGroup group, User member) {
        return this.joinOperation(group, member, FunUserJoinGroupEnum.JOIN_APPLY);
    }

    @Override
    @Transactional
    public boolean joinInvite(User member, FunGroup group, User inviteUser) {
        //fun_user_group表携带了一个邀请人字段，当邀请成员加入时使用(inviteUser)

        String ugId = IdGenerator.uuidStr();

        member = userMapper.queryUserByUserName(member.getUserName(),
                RoleEnum.USER.getRoleName(), RoleSettingStatus.SETTING.getStatus());
        group = groupMapper.getGroup(group.getSearchId());

        return userGroupMapper.inviteMember(ugId, group,
                member, FunUserJoinGroupEnum.JOIN_INVITE.getJoin(), inviteUser);
    }

    @Override
    @Transactional
    public boolean joinAccept(FunGroup group, User user) {
        //用户邀请通过和管理员同意加入都是通过此方法
        //设置flag-->给fun_user_role增加member角色
        return this.joinAcceptOperation(group, user);
    }


    private boolean joinOperation(FunGroup group, User member, FunUserJoinGroupEnum joinCheck) {

        String ugId = IdGenerator.uuidStr();
        group = groupMapper.getGroup(group.getSearchId());
        member = userMapper.queryUserByUserName(member.getUserName(),
                RoleEnum.USER.getRoleName(), RoleSettingStatus.SETTING.getStatus());

        return userGroupMapper.addMember(ugId, group, member, joinCheck.getJoin());
    }

    private boolean joinAcceptOperation(FunGroup group, User user) {
        //设置flag-->给fun_user_role增加member角色

        group = groupMapper.getGroup(group.getSearchId());
        user = userMapper.queryUserByUserName(user.getUserName(),
                RoleEnum.USER.getRoleName(), RoleSettingStatus.SETTING.getStatus());

        if (user.getUserId() != null && group.getGroupId() != null) {
            //通过组织id和用户id找到group id
            String ugId = userGroupMapper.queryUserGroupIdByUserIdAndGroupId(user.getUserId(),
                    group.getGroupId());
            userGroupMapper.updMemberJoinCheck(ugId, FunUserJoinGroupEnum.JOIN_PASS.getJoin());

            //新增 fun_user_role记录
            String fUserRoleId = IdGenerator.uuidStr();
            FunRole member = roleMapper.queryFunRoleByRoleName(FunRoleEnum.MEMBER.getRoleName());

            roleMapper.addFunUserRole(fUserRoleId, user, member, group,FunUsefulFlag.USE.getUse());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<FunMemberGroup> queryInviteGroup(String userId) {
        List<FunMemberGroup> memberGroups = null;
        if (userId != null)
            memberGroups = userGroupMapper.queryInformalityJoinInfo(userId, FunUserJoinGroupEnum.JOIN_INVITE.getJoin());
        return memberGroups;
    }

    @Override
    public List<User> queryJoinApply(String searchId, FunUserJoinGroupEnum joinGroupEnum) {
        FunGroup group = groupMapper.getGroup(searchId);
        return userGroupMapper.queryMembersByStatusAndGroupId(joinGroupEnum.getJoin(),group);
    }

    @Override
    @Transactional
    public boolean delGroup(String searchId) {
//        删除 user -  role 记录
        roleMapper.delAllUserRoles(searchId);
//        删除 user - group 记录
        userGroupMapper.removeAllMemberBySearchId(searchId);
//        删除 group 记录
        groupMapper.delGroup(searchId);
        return true;
    }
}