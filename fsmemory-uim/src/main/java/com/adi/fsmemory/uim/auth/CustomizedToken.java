package com.adi.fsmemory.uim.auth;

import com.adi.fsmemory.uim.constant.RoleEnum;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 定制化的token ，继承于UsernamePasswordToken
 */
public class CustomizedToken extends UsernamePasswordToken {

    private String loginType;

    public CustomizedToken() {
    }

    public CustomizedToken(String username, String password, RoleEnum roleEnum) {
        super(username, password);
        this.loginType = roleEnum.getRoleName();
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public void setLoginType(RoleEnum roleEnum) {
        this.loginType = roleEnum.getRoleName();
    }
}
