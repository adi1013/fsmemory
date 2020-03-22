package com.adi.fsmemory.uim.mapper;


import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FunGroupMapper {

    /**
     * 通过groupId查找fun_group
     */
    @Select("select * from fun_group where group_id=#{groupId}")
    @Results(id = "funGroupMap",value = {
            @Result(property = "groupId",      column = "group_id"),
            @Result(property = "groupName",    column = "group_name"),
            @Result(property = "userId",       column = "user_id"),
            @Result(property = "groupDesc",   column = "group_desc"),
            @Result(property = "searchId",     column = "search_id"),
            @Result(property = "createTime",   column = "create_time"),
            @Result(property = "updTime",      column = "upd_time")
    })
    FunGroup queryByGroupId(@Param("groupId") String groupId);

    /**
     * 新增组织
     */
    @Insert("insert into fun_group (group_id," +
                                   "user_id," +
                                   "group_name," +
                                   "group_desc," +
                                   "search_id) " +
                            "values (#{group.groupId}," +
                                    "#{group.userId}," +
                                    "#{group.groupName}," +
                                    "#{group.groupDesc}," +
                                    "#{group.searchId})")
    boolean addGroup(@Param("group") FunGroup group);


    /**
     * 查找所有searchId
     */
    @Select("select search_id from fun_group")
    List<String> queryAllSearchId();

    /**
     * 获取组织信息
     */
    @Select("select * from fun_group where search_id=#{searchId}")
    FunGroup getGroup(String searchId);


    /**
     * 获取组织下的所有人
     */
    @Select(" SELECT user.* FROM  " +
            " fun_group g " +
            " LEFT JOIN " +
            " fun_user_group ug " +
            " ON g.`group_id`=ug.`group_id` " +
            " INNER JOIN  " +
            " sys_user USER " +
            " ON user.`user_id`= ug.`user_id` " +
            " WHERE g.`search_id` = #{searchId}"+
            " and ug.flag=1")

    List<User> queryUsersBySearchId(String searchId);


    /**
     * 获取组织下的所有人以及其角色
     */


    /**
     * 删除组织
     */
    @Delete("delete from fun_group where search_id = #{searchId}")
    boolean delGroup(String searchId);

}
