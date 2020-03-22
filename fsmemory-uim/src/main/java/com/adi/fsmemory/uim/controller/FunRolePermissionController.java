package com.adi.fsmemory.uim.controller;

import com.adi.fsmemory.entity.uim.FunPermission;
import com.adi.fsmemory.entity.uim.FunRole;
import com.adi.fsmemory.restful.RestResultObj;
import com.adi.fsmemory.restful.RestResultType;
import com.adi.fsmemory.uim.constant.FunRoleEnum;
import com.adi.fsmemory.uim.service.FunRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fun/per")
public class FunRolePermissionController {

    @Autowired
    private FunRolePermissionService rolePerService;

    /**
     * 添加权限
     * @return
     */
    @RequestMapping(value = "add",method = {RequestMethod.POST,RequestMethod.PUT})
    public RestResultObj addPermission(@RequestBody FunPermission permission) {
        rolePerService.addFunPermission(permission);
        return RestResultType.SUCCESS.toRestfulResult(permission);
    }

    /**
     * 给管理员角色添加权限
     */
    @RequestMapping(value = "tomanager",method = {RequestMethod.POST, RequestMethod.PUT})
    public RestResultObj toManager(@RequestBody FunPermission permission){
        rolePerService.addFunRolePermission(/*FunRoleEnum.MANAGER*/FunRoleEnum.CREATOR, permission);
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 给成员角色添加权限
     */
    @RequestMapping(value = "tomember",method = {RequestMethod.POST, RequestMethod.PUT})
    public RestResultObj toMember(@RequestBody FunPermission permission) {
        rolePerService.addFunRolePermission(FunRoleEnum.MEMBER, permission);
        return RestResultType.SUCCESS.toRestfulResult(null);
    }


}
