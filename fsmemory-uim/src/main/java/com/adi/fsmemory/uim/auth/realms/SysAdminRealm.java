package com.adi.fsmemory.uim.auth.realms;

import com.adi.fsmemory.entity.uim.ActiveUser;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.uim.auth.CustomizedAuthorizingRealm;
import com.adi.fsmemory.uim.auth.CustomizedToken;
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

public class SysAdminRealm extends CustomizedAuthorizingRealm {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysUserPermissionService userPermissionService;

    public SysAdminRealm() {
    }

    public SysAdminRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        CustomizedToken token = (CustomizedToken) authenticationToken;
        String username = token.getUsername();

        User authenAdmin = userService.getAdmin(username);

        if (authenAdmin == null) {
            throw new UnknownAccountException("不存在该管理员");
        }

        String hashedCredentials = authenAdmin.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(authenAdmin.getSalt());

        Set<String> roles = userRoleService.getRoleStrsByUserName(username);
        Set<String> permissions = userPermissionService.getPermissionStrsByUserName(username);
        ActiveUser principal = new ActiveUser(authenAdmin,roles,permissions);         //生成活动对象放到info里面


        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt, this.getName());

        return info;
    }


}
