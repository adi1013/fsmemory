package com.adi.fsmemory.uim.constant;

public enum RoleEnum {
    ADMIN("admin"),
    USER("user"),
    GUEST("guest");


    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }
}
