package com.adi.fsmemory.uim.service;

import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.FunRole;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.constant.FunRoleEnum;
import com.adi.fsmemory.uim.constant.FunUsefulFlag;

public interface FunUserRoleService {

    /**
     * 增加或者解除组员角色
     * 通过FunUsefulFlag {@link FunUsefulFlag}
     */
    boolean addOrRemoveFunUserRole(User user, FunRoleEnum funRoleEnum, FunGroup group, FunUsefulFlag flag);


    /**
     * 新增某个角色
     */
    boolean addFunRole(FunRole funRole);

    /**
     * 移除成员
     * 查找其在组织中的所有角色后设置为 {@link FunUsefulFlag#UN_USE}
     * 在将 用户组织信息 删除
     */
    boolean removeMember(String userName, String searchId);

}
