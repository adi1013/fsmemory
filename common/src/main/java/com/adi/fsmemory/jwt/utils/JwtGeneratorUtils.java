package com.adi.fsmemory.jwt.utils;

import com.adi.fsmemory.jwt.JwtConfig;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class JwtGeneratorUtils {

    public enum StructName {
        HEADER(),BODY(),SIGNATURE();
    }

    private static JwtConfig jwtConfig = new JwtConfig();

    public JwtConfig getJwtConfig() {
        return jwtConfig;
    }

    public static JwtConfig getConfig() {
        return jwtConfig;
    }

    public void setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public static String createJWT(Map<String, Object> claims, String jwtId) {

        // 生成JWT的时间
        Date now =  DateTimeUtils.getNowDefault();

//        claims.put("issue",)

        //终止时间
        Date expiration = DateTimeUtils.plusOfMinutesForTarget(now, jwtConfig.getTtlMills());

        //secret部分
        SecretKey secret = genSecretKey();

        JwtBuilder builder = Jwts.builder()
                                 .setClaims(claims)   //claims 暂定为存放用户信息
                                 .setExpiration(expiration)
                                 .setIssuer(jwtConfig.getJwtIssue())
                                 .setIssuedAt(now)
                                 .setId(jwtId)
                                 .setSubject(jwtConfig.getJwtSubject())       //用户唯一标志
                                 .signWith(jwtConfig.getSignatureAlgorithm(), secret); //设置签名
        return builder.compact();
    }

    private static SecretKey genSecretKey() {
        SecretKeySpec secretKeySpec = null;
        byte[] bytes = Base64.getDecoder().decode(jwtConfig.getKey());
        secretKeySpec = new SecretKeySpec(bytes, 0, bytes.length, "AES");
        return secretKeySpec;
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Map parseJWT(String jwt) throws Exception {
        Map<StructName, Object> plainText = new LinkedHashMap<>();
        SecretKey key = genSecretKey();                           //签名秘钥，和生成的签名的秘钥一模一样
        Jws<Claims> claimsJws = Jwts.parser()                     //得到DefaultJwtParser
                                    .setSigningKey(key)           //设置签名的秘钥
                                    .parseClaimsJws(jwt);         //设置需要解析的jwt

        JwsHeader header = claimsJws.getHeader();
        Claims body = claimsJws.getBody();
        String signature = claimsJws.getSignature();
        plainText.put(StructName.HEADER,header);
        plainText.put(StructName.BODY,body);
        plainText.put(StructName.SIGNATURE,signature);
        return plainText;
    }
}
