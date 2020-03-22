package com.adi.fsmemory.uim.auth.realms;

import com.adi.fsmemory.entity.uim.ActiveUser;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.auth.CustomizedAuthorizingRealm;
import com.adi.fsmemory.uim.service.SysUserPermissionService;
import com.adi.fsmemory.uim.service.SysUserRoleService;
import com.adi.fsmemory.uim.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class SysUserRealm extends CustomizedAuthorizingRealm {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysUserPermissionService userPermissionService;

    public SysUserRealm() {
    }

    public SysUserRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String hashedCredentials = null;
        ByteSource credentialsSalt = null;
        ActiveUser principal = null;
        SimpleAuthenticationInfo info = null;

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        User authenUser = userService.getUser(username);

        if (authenUser == null) {
            throw new UnknownAccountException("不存在该用户");
        }

        hashedCredentials = authenUser.getPassword();
        credentialsSalt = ByteSource.Util.bytes(authenUser.getSalt());

        Set<String> roles = userRoleService.getRoleStrsByUserName(username);
        Set<String> permissions = userPermissionService.getPermissionStrsByUserName(username);
        principal = new ActiveUser(authenUser,roles,permissions);         //生成活动对象放到info里面


        info = new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt, this.getName());

        return info;
    }

}
