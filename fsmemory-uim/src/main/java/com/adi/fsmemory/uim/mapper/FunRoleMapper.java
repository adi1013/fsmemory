package com.adi.fsmemory.uim.mapper;


import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.FunPermission;
import com.adi.fsmemory.entity.uim.FunRole;
import com.adi.fsmemory.entity.uim.User;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;

@Repository
@Mapper
public interface FunRoleMapper {

    /**
     * 查找组织角色
     */
    @Select("select * from fun_role where f_role_name = #{fRoleName}")
    FunRole queryFunRoleByRoleName(String fRoleName);

    /**
     * 删除组织角色
     */



    /**
     * 更新组织角色
     */


    /**
     * 增加组织角色
     */
    @Insert("insert into fun_role(f_role_id,f_role_name,f_role_desc) values (#{fRoleId},#{fRoleName},#{fRoleDesc})")
    boolean addFunRole(FunRole role);

    /**
     * 查找某个用户在组织中的角色
     */
    @Select(" SELECT r.* FROM  " +
            " fun_group g " +
            " LEFT JOIN " +
            " fun_user_group ug " +
            " ON g.`group_id`=ug.`group_id` " +
            " INNER JOIN " +
            " sys_user u " +
            " ON u.`user_id` = ug.`user_id` " +
            " LEFT JOIN  " +
            " fun_user_role ur " +
            " ON ur.`user_id` = u.`user_id` AND ur.`group_id` = ug.`group_id` " +
            " INNER JOIN " +
            " fun_role r " +
            " ON r.`f_role_id` = ur.`f_role_id` " +
            " WHERE u.`user_name` = #{userName}  " +
            "   AND g.`search_id` = #{searchId} " +
            "   AND ug.`join_check` = #{joinCheck}"
    )
    List<FunRole> queryFunRolesByUserName(@Param("userName") String userName,
                                       @Param("searchId") String searchId,
                                       @Param("joinCheck") Integer joinCheck);


    /**
     * 给角色增加权限
     */
    @Insert("insert into fun_role_perm(f_role_perm_id," +
                                      "f_role_id," +
                                      "f_perm_id)" +
                               "values(#{rolePermId}," +
                                      "#{role.fRoleId}," +
                                      "#{per.fPermId})")
    boolean addFunRolePermission(@Param("rolePermId")String rolePermId,
                                 @Param("role") FunRole funRole,
                                 @Param("per")FunPermission per);


    /**
     * 增加用户角色
     */
    @Insert("insert into fun_user_role (f_user_role_id," +
                                       "user_id," +
                                       "f_role_id," +
                                       "group_id,"+
                                       "flag) " +
                               "values (#{fUserRoleId}," +
                                       "#{user.userId}," +
                                       "#{role.fRoleId}," +
                                       "#{group.groupId}," +
                                       "#{flag})")
    boolean addFunUserRole(@Param("fUserRoleId") String fUserRoleId,
                           @Param("user") User user,
                           @Param("role") FunRole role,
                           @Param("group") FunGroup group,
                           @Param("flag") Integer flag);


    /**
     * 通过 组织id ，用户id， 角色id 来唯一索引一个组中某角色的用户
     * @param groupId
     * @param userId
     * @param fRoleId
     * @return
     */
    @Select("select f_user_role_id from fun_user_role " +
            " where group_id=#{groupId} " +
              " and user_id=#{userId} " +
              " and f_role_id=#{fRoleId}")
    String queryUserRoleIdUnique(@Param("groupId") String groupId,
                                 @Param("userId") String userId,
                                 @Param("fRoleId") String fRoleId );

    @Update("update fun_user_role set flag = #{flag} where f_user_role_id=#{urId}")
    boolean updUserRoleUseByURId(@Param("flag") Integer flag, @Param("urId") String urId);

    /**
     * 查找某个用户在组织中的所有 user-role角色记录id
     */


    /**
     * 查找某个用户在组织中的角色
     */
    @Update("update fun_user_role " +
            "set flag =  #{flag} " +
            "where user_id in (select user_id from sys_user where user_name=#{userName})" +
            "and group_id in (select group_id from fun_group where search_id = #{searchId})")
    boolean updUserRoleFlag(@Param("userName") String userName,
                            @Param("searchId") String searchId,
                            @Param("flag") Integer flag);

    /**
     * 删除某个组织下所有成员的 用户 - 角色记录
     */
    @Delete(" delete from fun_user_role " +
            " where " +
            " group_id in (select group_id from fun_group where search_id = #{searchId})")
    boolean delAllUserRoles(String searchId);
}
