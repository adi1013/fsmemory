package com.adi.fsmemory.uim.mapper;


import com.adi.fsmemory.entity.uim.SysPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionMapper {

    @Insert("insert into sys_permission(per_id,per_name,per_desc) values (#{perId},#{perName},#{perDesc})")
    boolean addPermission(SysPermission sysPermission);

    @Select("select * from sys_permission")
    List<SysPermission> queryAllPermissions();

    @Select("select * from sys_permission where per_name = #{perName}")
    SysPermission queryPermissionByPermissionName(String perName);

}
