package com.adi.fsmemory.uim.controller;

import com.adi.fsmemory.entity.uim.ActiveUser;
import com.adi.fsmemory.entity.uim.SysRole;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.restful.RestResultObj;
import com.adi.fsmemory.restful.RestResultType;
import com.adi.fsmemory.uim.auth.CustomizedToken;
import com.adi.fsmemory.uim.constant.RoleEnum;
import com.adi.fsmemory.uim.constant.RoleSettingStatus;
import com.adi.fsmemory.uim.service.SysRoleService;
import com.adi.fsmemory.uim.service.SysUserRoleService;
import com.adi.fsmemory.uim.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class SysAdminLoginController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService uRService;


    /**
     * 管理员登录
     * @param user
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public RestResultObj login(@RequestBody User user) {
        CustomizedToken token = new CustomizedToken(user.getUserName(), user.getPassword(), RoleEnum.ADMIN);
        Subject currentAdmin = SecurityUtils.getSubject();
        try {
            currentAdmin.login(token);
        } catch (Exception e) {
            throw e;
        }
        ActiveUser principal = (ActiveUser) currentAdmin.getPrincipal();
        //将用户信息放到redis或者sessionManager

        //
        return RestResultType.SUCCESS.toRestfulResult(principal);
    }

    /**
     * 直接注册为管理员，即本来不存在用户
     * @param user
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public synchronized RestResultObj adminRegisterApply(@RequestBody User user) {

        SysRole sysRole = roleService.getRole(RoleEnum.ADMIN);
        userService.addUser(user);
        uRService.addSysRole(user, sysRole, RoleSettingStatus.SETTING_APPLY);

        return RestResultType.SUCCESS.toRestfulResult(user);
    }


    /**
     * 设置管理员
     * 即给某用户(已存在)增加管理员角色
     * @param info
     * @return
     */
    @RequestMapping(value = "/set/apply", method = {RequestMethod.PUT,RequestMethod.POST})
    public RestResultObj setUserAdmin(@RequestBody User info) {
        //先获取已有用户
        User user = userService.getUser(info.getUserName());
        if (user != null) {
            SysRole sysRole = roleService.getRole(RoleEnum.ADMIN);
            uRService.addSysRole(user, sysRole, RoleSettingStatus.SETTING_APPLY);
        }
        return RestResultType.SUCCESS.toRestfulResult(null);
    }


    /**
     * 通过管理员设定申请
     * @param info
     * @return
     */
    @RequestMapping(value = "/set/via", method = {RequestMethod.PUT,RequestMethod.POST})
    public RestResultObj adminRegisterViaApply(@RequestBody User info) {
        User user = this.setAdminRoleFlag(info.getUserName(),
                RoleSettingStatus.SETTING_APPLY, RoleSettingStatus.SETTING);
        return RestResultType.SUCCESS.toRestfulResult(user);
    }

    /**
     * 申请解除某位用户的管理员角色
     * @param info
     * @return
     */
    @RequestMapping(value = "/abondon/apply", method = {RequestMethod.PUT,RequestMethod.POST})
    public RestResultObj adminAbondonApply(@RequestBody User info) {
        User user = this.setAdminRoleFlag(info.getUserName(),
                RoleSettingStatus.SETTING, RoleSettingStatus.ABANDON_APPLY);
        return RestResultType.SUCCESS.toRestfulResult(user);
    }

    /**
     * 通过解除某位用户的管理员角色的申请
     * @param info
     * @return
     */
    @RequestMapping(value = "/abondon/via", method = {RequestMethod.PUT,RequestMethod.POST})
    public RestResultObj adminAbondonViaApply(@RequestBody User info) {
        User user = this.setAdminRoleFlag(info.getUserName(),
                RoleSettingStatus.ABANDON_APPLY, RoleSettingStatus.ABANDON);
        return RestResultType.SUCCESS.toRestfulResult(user);
    }

    /**
     * 重新启用某个管理员
     * @param info
     * @return
     */
    @RequestMapping(value = "/reuse", method = {RequestMethod.PUT,RequestMethod.POST})
    public RestResultObj reUseAdmin(@RequestBody User info) {
        User user = this.setAdminRoleFlag(info.getUserName(),
                RoleSettingStatus.ABANDON, RoleSettingStatus.SETTING);
        return RestResultType.SUCCESS.toRestfulResult(user);
    }

    private User setAdminRoleFlag(String userName, RoleSettingStatus beforeStatus, RoleSettingStatus afterStatus) {
        User user = userService.getAdmin(userName,beforeStatus);
        SysRole sysRole = roleService.getRole(RoleEnum.ADMIN);
        uRService.updUserRoleFlag(user, sysRole, afterStatus);
        return user;
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout")
    public RestResultObj logout() {
        SecurityUtils.getSubject().logout();
        return RestResultType.SUCCESS.toRestfulResult(null);
    }


}
