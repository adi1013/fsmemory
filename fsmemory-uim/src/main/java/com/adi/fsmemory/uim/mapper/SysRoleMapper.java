package com.adi.fsmemory.uim.mapper;

import com.adi.fsmemory.entity.uim.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleMapper {


    @Insert("insert into sys_role(role_id,role_name,role_desc) values (#{roleId},#{roleName},#{roleDesc})")
    boolean addRole(SysRole sysRole);

    @Select("select * from sys_role")
    List<SysRole> queryAllRoles();

    @Select("select * from sys_role where role_name=#{roleName}")
    SysRole queryRoleByRoleName(String roleName);
}
