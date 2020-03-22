package com.adi.fsmemory.uim.auth;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.AuthorizingRealm;

/**
 * 定制化的Realm
 */
public abstract class CustomizedAuthorizingRealm extends AuthorizingRealm {
    private CustomizedRealmName customizedRealmName;

    public CustomizedAuthorizingRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

    public CustomizedAuthorizingRealm() {
    }

    public CustomizedRealmName getCustomizedRealmName() {
        return customizedRealmName;
    }

    public void setCustomizedRealmName(CustomizedRealmName customizedRealmName) {
        this.customizedRealmName = customizedRealmName;
    }
}
