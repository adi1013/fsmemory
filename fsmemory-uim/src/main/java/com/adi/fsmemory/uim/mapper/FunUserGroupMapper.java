package com.adi.fsmemory.uim.mapper;

import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.FunMemberGroup;
import com.adi.fsmemory.entity.uim.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FunUserGroupMapper {

    /**
     * 邀请成员加入到组织
     */
    @Insert("insert into fun_user_group(user_group_id," +
                                       "group_id," +
                                       "user_id," +
                                       "invited_member,"+
                                       "join_check) " +
                               "values (#{ugId}," +
                                       "#{group.groupId}," +
                                       "#{member.userId}," +
                                       "#{invite.userId},"+
                                       "#{joinCheck})")

    boolean inviteMember(@Param("ugId") String ugId,
                         @Param("group") FunGroup group,
                         @Param("member") User member,
                         @Param("joinCheck") Integer joinCheck,
                         @Param("invite") User invite);

    /**
     * 加入成员到组织
     */
    @Insert("insert into fun_user_group(user_group_id," +
                                       "group_id," +
                                       "user_id," +
                                       "join_check) " +
                              " values (#{ugId}," +
                                       "#{group.groupId}," +
                                       "#{member.userId}," +
                                       "#{joinCheck})")
    boolean addMember(@Param("ugId") String ugId,
                      @Param("group") FunGroup group,
                      @Param("member") User member,
                      @Param("joinCheck") Integer joinCheck);


    /**
     * 更新成员加入组的状态
     */
    @Update(" update fun_user_group " +
               " set join_check=#{joinCheck}" +
             " where user_group_id=#{ugId} ")
    boolean updMemberJoinCheck(@Param("ugId") String ugId, @Param("joinCheck") Integer joinCheck);

    /**
     * 根据用户id和组织id找到 用户--组织 id
     * @param userId
     * @param groupId
     * @return
     */
    @Select(" select user_group_id " +
            " from fun_user_group " +
            " where user_id=#{userId} and group_id=#{groupId}")
    String queryUserGroupIdByUserIdAndGroupId(@Param("userId") String userId, @Param("groupId") String groupId);


    /**
     * 根据用户id找到组织
     * @param userId     用户id
     * @param joinCheck
     * @return
     */
    @Select("select * from fun_user_group where join_check = #{joinCheck} and user_id=#{userId}")
    @Results(id = "userGroupMap",value = {
            @Result(column = "user_group_id",property = "ugId"),
            @Result(column = "group_id",property = "group"
                    ,one = @One(select = "com.adi.fsmemory.uim.mapper.FunGroupMapper.queryByGroupId")),
//            @Result(column = "user_id",property = "user",
//                    one = @One(select = "com.adi.fsmemory.uim.mapper.SysUserMapper.queryByUserId")),
            @Result(column = "join_time",property = "joinTime"),
            @Result(column = "join_check",property = "joinCheck"),
            @Result(column = "invited_member",property = "inviterUser",
                    one = @One(select = "com.adi.fsmemory.uim.mapper.SysUserMapper.queryByUserId")),
    })
    List<FunMemberGroup> queryInformalityJoinInfo(@Param("userId") String userId, @Param("joinCheck") Integer joinCheck);

    /**
     * 根据组织id找到处于某种状态的用户
     */
    @Select(" SELECT u.* FROM " +
            " fun_user_group ug " +
            " LEFT JOIN " +
            " sys_user u " +
            " ON ug.user_id = u.user_id " +
            " WHERE ug.`group_id`= #{group.groupId}" +
            "   AND ug.`join_check`= #{status}")
    List<User> queryMembersByStatusAndGroupId(@Param("status") Integer status,
                                              @Param("group") FunGroup group);

    /**
     * 移除成员
     */
    @Delete(" delete from fun_user_group " +
            " where " +
            " group_id in (select group_id from fun_group where search_id = #{searchId})" +
            " and user_id in (select user_id from sys_user where user_name=#{userName})")
    boolean removeMember(@Param("userName") String userName,
                         @Param("searchId") String searchId);


    /**
     * 移除某个组织下的所有成员
     */
    @Delete(" delete from fun_user_group " +
            " where group_id in (select group_id from fun_group where search_id = #{searchId})")
    boolean removeAllMemberBySearchId(String searchId);
}
