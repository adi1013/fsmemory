package com.adi.fsmemory.uim.service;

import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.FunMemberGroup;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.constant.FunUserJoinGroupEnum;

import java.util.List;

public interface FunGroupService {

    /**
     * 增加组织
     */
    boolean addGroup(FunGroup group, User creator);

    /**
     * 查找组织
     */
    List<FunGroup> queryGroupBySearchId(String searchId);

    /**
     * 查找某个用户创建的组织
     */
    List<FunGroup> queryGroupByUserName(String userName);

    /**
     * 退出组织
     */
    boolean abortGroup(FunGroup group);

    /**
     * 申请加入组织
     */
    boolean joinApply(FunGroup group, User member) ;

    /**
     * 邀请加入组织
     */
    boolean joinInvite(User member, FunGroup group, User inviteUser) ;

    /**
     * 邀请通过或者申请通过
     * @param group
     * @param user
     * @return
     */
    boolean joinAccept(FunGroup group, User user);

    /**
     * 通过用户id找到邀请该用户加入的组织
     */
    List<FunMemberGroup> queryInviteGroup(String userId);


    /**
     * 管理员查找到申请加入的信息
     */
    List<User> queryJoinApply(String searchId, FunUserJoinGroupEnum joinGroupEnum);

    /**
     * 解散组织
     * 删除 user -  role 记录
     * 删除 user - group 记录
     * 删除 group 记录
     */
    boolean delGroup(String searchId);
}
