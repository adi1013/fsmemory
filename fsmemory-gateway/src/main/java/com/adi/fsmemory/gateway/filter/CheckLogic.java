package com.adi.fsmemory.gateway.filter;

import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.gateway.service.UIMService;
import com.adi.fsmemory.jwt.JwtCheck;
import com.adi.fsmemory.jwt.TimeOutException;
import com.adi.fsmemory.jwt.utils.JwtGeneratorUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于对token信息的验证
 */
@Component
public class CheckLogic implements JwtCheck {

    @Autowired
    private UIMService uimService;

    @Override
    public boolean check(Map<JwtGeneratorUtils.StructName, Object> plainText) throws TimeOutException {
        JwsHeader header = (JwsHeader)plainText.get(JwtGeneratorUtils.StructName.HEADER);
        Claims jwtBody = (Claims) plainText.get(JwtGeneratorUtils.StructName.BODY);

        Map onlineUser = (Map<String, Object>) (((Map<String, Object>) jwtBody.get("user")).get("onlineUser"));

        //验证联合主键，如果存在的话
        String userName = covertStr( onlineUser.get("userName"));
        String password = covertStr( onlineUser.get("password"));
        String jwtId = covertStr(jwtBody.get("jwtId"));

        User info = uimService.getUserSecretInfo(userName);

        if (info == null || StringUtils.isEmpty(password))
            return false;

        if (this.checkSecret(info, password, jwtId))
            return true;

        return false;
    }

    private String covertStr(Object obj) {
        return String.valueOf(obj);
    }


    private boolean checkSecret(User info, String password, String jwtId) {
        if ( password.equals(info.getPassword()) && jwtId.equals(info.getUserId()) ) {
            return true;
        }
        return false;
    }


    private boolean checkHeader(JwsHeader header, User info) {
        if (header.getKeyId().equals(info.getUserId())) {
            return true;
        }
        return false;
    }
}
