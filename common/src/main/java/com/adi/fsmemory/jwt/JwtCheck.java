package com.adi.fsmemory.jwt;

import com.adi.fsmemory.jwt.utils.JwtGeneratorUtils;
import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * jwt 校验接口，业务端可实现该接口后注入{@link JwtCheckProxy}
 */
public interface JwtCheck {

    boolean check(Map<JwtGeneratorUtils.StructName, Object> plainText) throws TimeOutException;
}
