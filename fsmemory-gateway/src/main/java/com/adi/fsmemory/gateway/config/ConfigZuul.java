package com.adi.fsmemory.gateway.config;

import com.adi.fsmemory.gateway.filter.CheckLogic;
import com.adi.fsmemory.gateway.filter.NonCheckConfig;
import com.adi.fsmemory.gateway.filter.TokenCheckFilter;
import com.adi.fsmemory.jwt.JwtCheck;
import com.adi.fsmemory.jwt.JwtCheckProxy;
import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigZuul {

    @Bean
    @Autowired
    public ZuulFilter tokenCheckFilter(NonCheckConfig nonCheckConfig) {
        TokenCheckFilter tokenCheckFilter =  new TokenCheckFilter();
        tokenCheckFilter.setNonCheckConfig(nonCheckConfig);
        return tokenCheckFilter;
    }

    @Bean
//    @ConditionalOnProperty(prefix = "com.fsmemory")
    @ConfigurationProperties(prefix = "com.fsmemory",ignoreUnknownFields = true)
    public NonCheckConfig nonCheckConfig() {
        return new NonCheckConfig();
    }

    @Bean
    public JwtCheck jwtCheck() {
        return new CheckLogic();
    }

    @Bean
    @Autowired
    public JwtCheckProxy jwtCheckProxy(JwtCheck jwtCheck) {
        return new JwtCheckProxy(jwtCheck);
    }
}
