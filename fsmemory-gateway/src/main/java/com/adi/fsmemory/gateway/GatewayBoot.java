package com.adi.fsmemory.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.adi.fsmemory.gateway.service"})
public class GatewayBoot {

    public static void main(String[] args) {
        SpringApplication.run(GatewayBoot.class, args);
    }
}

