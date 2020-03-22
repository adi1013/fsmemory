package com.adi.fsmemory.jwt;

import com.adi.fsmemory.jwt.utils.JwtGeneratorUtils;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * jwt 配置类，业务端自定义规则后注入{@link JwtGeneratorUtils}
 */
public class JwtConfig {

    private long ttlMills = 15*60;   //默认为秒

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private String key = "adc4a4658c91a351c4022bdd722e50982ecc02bd3fe01ec52239a74fb63c3c5a45879cab3ee6581508b9d032c23c0c9a9ace604743367c96461cf43bb8f869c0";

    private String jwtIssue = "FSMEMORY_UIM";

    private String jwtSubject = "FSMEMORY";

    public long getTtlMills() {
        return ttlMills;
    }

    public void setTtlMills(long ttlMills) {
        this.ttlMills = ttlMills;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJwtIssue() {
        return jwtIssue;
    }

    public void setJwtIssue(String jwtIssue) {
        this.jwtIssue = jwtIssue;
    }

    public String getJwtSubject() {
        return jwtSubject;
    }

    public void setJwtSubject(String jwtSubject) {
        this.jwtSubject = jwtSubject;
    }
}
