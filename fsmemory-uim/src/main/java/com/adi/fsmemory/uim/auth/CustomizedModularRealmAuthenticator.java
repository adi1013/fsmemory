package com.adi.fsmemory.uim.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 定制多种登录时的校验规则
 */
public class CustomizedModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {

        this.assertRealmsConfigured();
        Collection<Realm> realms = this.getRealms();
        Collection<Realm> typeRealms = new ArrayList<>();


        if (authenticationToken instanceof CustomizedToken) {
            CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
            String loginType = customizedToken.getLoginType();

            for (Realm r : realms) {
                if (r instanceof CustomizedAuthorizingRealm) {
                    CustomizedAuthorizingRealm customizedRealm = (CustomizedAuthorizingRealm) r;
                    if (customizedRealm.getCustomizedRealmName().getRealmName().equals(loginType)){
                        typeRealms.add(r);
                    }
                }
            }
        }

        if (typeRealms.size() == 1) {
            return this.doSingleRealmAuthentication(typeRealms.iterator().next(), authenticationToken);
        } else {
            return this.doMultiRealmAuthentication(typeRealms, authenticationToken);
        }
    }
}
