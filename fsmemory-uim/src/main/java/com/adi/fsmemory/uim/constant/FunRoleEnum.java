package com.adi.fsmemory.uim.constant;

public enum FunRoleEnum {
    CREATOR("creator"),
    MANAGER("manager"),
    MEMBER("member");

    private String roleName;

    FunRoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
