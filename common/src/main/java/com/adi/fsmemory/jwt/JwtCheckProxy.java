package com.adi.fsmemory.jwt;

import com.adi.fsmemory.jwt.utils.DateTimeUtils;
import com.adi.fsmemory.jwt.utils.JwtGeneratorUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;

import java.util.Map;


/**
 * jwt token 校验
 */
public class JwtCheckProxy implements JwtCheck {

    private JwtCheck jwtCheck;

    public JwtCheckProxy(JwtCheck jwtCheck) {
        this.jwtCheck = jwtCheck;
    }

    public JwtCheck getJwtCheck() {
        return jwtCheck;
    }

    public void setJwtCheck(JwtCheck jwtCheck) {
        this.jwtCheck = jwtCheck;
    }

    @Override
    public boolean check(Map<JwtGeneratorUtils.StructName, Object> plainText) throws TimeOutException {
        try {
            Claims claims = (Claims) plainText.get(JwtGeneratorUtils.StructName.BODY);
            JwsHeader header = (JwsHeader)plainText.get(JwtGeneratorUtils.StructName.HEADER);
            JwtConfig jwtConfig = JwtGeneratorUtils.getConfig();

            //先判断是否已经过期
            if (claims != null && claims.getExpiration() != null) {
                assertTimeout(claims);
            }
            //判断算法\签发者等是否相同
            if (header.getAlgorithm().equals(jwtConfig.getSignatureAlgorithm().getValue())
                    && claims.getIssuer().equals(jwtConfig.getJwtIssue())
                    && claims.getSubject().equals(jwtConfig.getJwtSubject()))
                return jwtCheck.check(plainText);
        } catch (NullPointerException e) {
            return false;
        } catch (Exception e) {
            throw e;
        }

        return false;
    }

    public void assertTimeout(Claims claims) {
        boolean previous = DateTimeUtils.isItPrevious(claims.getExpiration());

        if( !previous )
            return;
        else
            throw new TimeOutException("token已经超时");
    }
}
