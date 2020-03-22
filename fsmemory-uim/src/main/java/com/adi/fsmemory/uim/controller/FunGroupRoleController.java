package com.adi.fsmemory.uim.controller;

import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.FunRole;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.restful.RestResultObj;
import com.adi.fsmemory.restful.RestResultType;
import com.adi.fsmemory.uim.constant.FunRoleEnum;
import com.adi.fsmemory.uim.constant.FunUsefulFlag;
import com.adi.fsmemory.uim.service.FunUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("fun/role")
public class FunGroupRoleController {

    @Autowired
    private FunUserRoleService furSrevice;

    /**
     * 增加角色
     */
    @RequestMapping(value = "add",method = {RequestMethod.POST,RequestMethod.PUT})
    public RestResultObj addRole(@RequestBody FunRole role) {
        furSrevice.addFunRole(role);
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 组织创建者设置某用户为管理员
     * 目标用户
     */
    @RequestMapping(value = "addition",method = {RequestMethod.POST,RequestMethod.PUT})
    public RestResultObj addManagerRole(@RequestBody Map<String,Object> params) {
        User targetUser = new User();
        targetUser.setUserName(params.get("userName").toString());
        FunGroup targetGroup = new FunGroup();
        targetGroup.setSearchId(params.get("searchId").toString());
        furSrevice.addOrRemoveFunUserRole(targetUser, FunRoleEnum.MANAGER,targetGroup,FunUsefulFlag.USE);
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 组织创建者取消某管理员
     */
    @RequestMapping(value = "eliminate",method = {RequestMethod.POST,RequestMethod.PUT})
    public RestResultObj removeManagerRole(@RequestBody Map<String,Object> params) {
        User targetUser = new User();
        targetUser.setUserName(params.get("userName").toString());
        FunGroup targetGroup = new FunGroup();
        targetGroup.setSearchId(params.get("searchId").toString());
        furSrevice.addOrRemoveFunUserRole(targetUser, FunRoleEnum.MANAGER,targetGroup,FunUsefulFlag.UN_USE);
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 设置普通成员
     */
//    @RequestMapping(value = "touser",method = {RequestMethod.POST,RequestMethod.PUT})
//    public RestResultObj addUserRole(@RequestBody User user) {
//        furSrevice.addFunUserRole(user, FunRoleEnum.MEMBER)
//    }

}
