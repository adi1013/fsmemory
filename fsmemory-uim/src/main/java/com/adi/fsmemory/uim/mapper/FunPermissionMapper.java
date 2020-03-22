package com.adi.fsmemory.uim.mapper;


import com.adi.fsmemory.entity.uim.FunPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FunPermissionMapper {

    /**
     * 查找权限
     */
    @Select("select * from fun_permission where f_perm_name = #{fPermName}")
    FunPermission queryFunPermByName(String fPermName);

    /**
     * 删除权限
     */


    /**
     * 更新权限
     */


    /**
     * 增加权限
     */
    @Insert("insert into fun_permission(f_perm_id,f_perm_name,f_perm_desc) values (#{fPermId},#{fPermName},#{fPermDesc})")
    boolean addFunPermission(FunPermission funPermission);
}