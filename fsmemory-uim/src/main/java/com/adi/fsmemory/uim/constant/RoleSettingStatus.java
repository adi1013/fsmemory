package com.adi.fsmemory.uim.constant;

/**
 * 此枚举用于设置管理员时的业务状态处理
 * 设置管理员    管理员设置--->  审核  --->启用
 * 废除管理员    取消管理员--->  审核  --->弃用
 */
public enum RoleSettingStatus {
    SETTING_APPLY(1),
    SETTING(2),
    ABANDON_APPLY(3),
    ABANDON(4);

    int status;

    RoleSettingStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
