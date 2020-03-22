package com.adi.fsmemory.uim.mapper;


import com.adi.fsmemory.entity.uim.SysRole;
import com.adi.fsmemory.entity.uim.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserRoleMapper {


    @Insert("insert into sys_user_role(ur_id," +
                                      "user_id," +
                                      "role_id," +
                                      "flag  ) " +
                              "values (#{urId}," +
                                      "#{user.userId}," +
                                      "#{sysRole.roleId}," +
                                      "#{flag} )")
    boolean addUserRole(@Param("urId") String urId,
                        @Param("user") User user,
                        @Param("sysRole") SysRole sysRole,
                        @Param("flag") Integer flag);

    @Select(" SELECT role.* FROM " +
            " sys_user USER " +
            " LEFT JOIN " +
            " sys_user_role ur " +
            " ON user.`user_id` = ur.`user_id`" +
            " LEFT JOIN " +
            " sys_role role " +
            " ON ur.`role_id` = role.`role_id` " +
            " WHERE user.`user_name` = #{userName} ")
    List<SysRole> queryUserRolesByUserName(String userName);



    @Update("update sys_user_role " +
               "set flag = #{flag} " +
             "where role_id=#{sysRole.roleId} " +
               "and user_id=#{user.userId}")
    boolean updUserRoleFlag(@Param("user") User user, @Param("sysRole") SysRole sysRole, @Param("flag") Integer flag);

}
