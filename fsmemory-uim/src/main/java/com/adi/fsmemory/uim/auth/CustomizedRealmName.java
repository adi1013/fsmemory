package com.adi.fsmemory.uim.auth;

import com.adi.fsmemory.uim.constant.RoleEnum;

public class CustomizedRealmName {

    private String realmName;

    public CustomizedRealmName(String realmName) {
        this.realmName = realmName;
    }

    public CustomizedRealmName() {
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public void setRealmName(RoleEnum roleEnum) {
        this.realmName = roleEnum.getRoleName();
    }

}
