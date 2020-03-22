package com.adi.fsmemory.gateway.filter;

import org.springframework.beans.factory.annotation.Value;

public class NonCheckConfig {

    @Value(value="com.fsmemory.skipCheckUrls")
    private String[] skipCheckUrls;

    public String[] getSkipCheckUrls() {
        return skipCheckUrls;
    }

    public void setSkipCheckUrls(String[] skipCheckUrls) {
        this.skipCheckUrls = skipCheckUrls;
    }
}
