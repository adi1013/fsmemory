package com.adi.fsmemory.uim.mapper;

import com.adi.fsmemory.entity.uim.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserMapper {

    @Insert("insert into sys_user(user_id,tel,password,salt,user_name) values (#{userId},#{tel},#{password},#{salt},#{userName})")
    boolean addUser(User user);



    @Select("select * from sys_user")
    List<User> queryUsers();


    @Select("select * from sys_user where user_id=#{userId}")
    @Results(id = "userMap",value = {
            @Result(property = "userId",  column = "user_id"),
            @Result(property = "tel",     column = "tel"),
            @Result(property = "password",column = "password"),
            @Result(property = "salt",   column = "salt"),
            @Result(property = "userName",column = "user_name")
    })
    User queryByUserId(@Param("userId") String userId);

    @Select(" SELECT user.* FROM " +
            " sys_user USER " +
            " LEFT JOIN sys_user_role ur " +
            " ON user.user_id=ur.user_id " +
            " LEFT JOIN " +
            " sys_role role " +
            " ON ur.role_id = role.role_id" +
            " WHERE " +
            " user.user_name=#{userName} " +
            " AND " +
            " role.role_name=#{roleName}" +
            " AND " +
            " ur.flag=#{flag}")

    User queryUserByUserName(@Param("userName") String userName,
                             @Param("roleName") String roleName,
                             @Param("flag") Integer flag);

}
