package com.adi.fsmemory.uim.mapper;

import com.adi.fsmemory.entity.uim.SysPermission;
import com.adi.fsmemory.entity.uim.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserPermissionMapper {

//    @SelectProvider(type = UserSqlProvider.class,method = )
    @Select(" SELECT per.* FROM " +
            " sys_user USER " +
            " LEFT JOIN " +
            " sys_user_role ur " +
            " ON user.`user_id` = ur.`user_id` " +
            " LEFT JOIN " +
            " sys_role_per rp " +
            " ON rp.role_id = ur.role_id " +
            " LEFT JOIN " +
            " sys_permission per " +
            " ON per.per_id = rp.per_id " +
            " WHERE user.`user_name` = #{userName}")
    List<SysPermission> queryPermissionsByUserName(String userName);

    @Insert("insert into sys_role_per(rp_id,role_id,per_id) values (#{rpId},#{sysRole.roleId},#{sysPermission.perId})")
    boolean addRolePer(@Param("rpId")String rpId, @Param("sysRole") SysRole sysRole, @Param("sysPermission") SysPermission sysPermission);
}
