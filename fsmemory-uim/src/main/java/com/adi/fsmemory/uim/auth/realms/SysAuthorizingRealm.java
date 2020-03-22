package com.adi.fsmemory.uim.auth.realms;

import com.adi.fsmemory.entity.uim.ActiveUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class SysAuthorizingRealm extends AuthorizingRealm {


    public SysAuthorizingRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        ActiveUser currentUser = (ActiveUser)principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //将信息放在ActiveUser对象中，是防止这里多频繁查库
        if (currentUser.getRoles() != null &&currentUser.getRoles().size() > 0) {
            authorizationInfo.addRoles(currentUser.getRoles());
        }

        if (currentUser.getPermissions() != null &&currentUser.getPermissions().size() > 0) {
            authorizationInfo.addStringPermissions(currentUser.getPermissions());
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
