package com.adi.fsmemory.uim.controller;

import com.adi.fsmemory.entity.uim.ActiveUser;
import com.adi.fsmemory.entity.uim.SysRole;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.expose.UserCheckFeign;
import com.adi.fsmemory.jwt.utils.JwtGeneratorUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
public class SysUserLoginController implements UserCheckFeign {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService urService;

    @Autowired
    private SysRoleService roleService;


    public RestResultObj test() {
        return RestResultType.SUCCESS.toRestfulResult("test");
    }

    /**
     * 这里除了密码正确与否外，还需要判断该用户是否带有user角色
     * @param user
     * @return
     */
    public RestResultObj login(@RequestBody User user) {
        CustomizedToken token = new CustomizedToken(user.getUserName(), user.getPassword(), RoleEnum.USER);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (Exception e) {
            throw e;
        }
        ActiveUser principal = (ActiveUser) currentUser.getPrincipal();

        //返回jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", principal);
        String jwtToken = JwtGeneratorUtils.createJWT(claims,
                principal.getOnlineUser().getUserId());
        return RestResultType.SUCCESS.toRestfulResult(jwtToken);
    }


    /**
     * 用户注册
     * 存储用户信息 --->  设置user角色
     * @param user
     * @return
     */
    public RestResultObj userRegister(@RequestBody User user) {
        userService.addUser(user);
        SysRole sysRole = roleService.getRole(RoleEnum.USER);
        urService.addSysRole(user, sysRole, RoleSettingStatus.SETTING);
        return RestResultType.SUCCESS.toRestfulResult(user);
    }


    /**
     * 退出登录
     */
    public RestResultObj logout() {
        SecurityUtils.getSubject().logout();
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    @Override
    public User getUserSecretInfo(@PathVariable(value = "userName") String userName) {
        User user = userService.getUser(userName);
        return user;
    }
}
