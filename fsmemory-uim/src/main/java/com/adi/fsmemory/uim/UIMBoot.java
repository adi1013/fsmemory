package com.adi.fsmemory.uim;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan(basePackages = {"com.adi.fsmemory.uim.mapper"})            //mybatis
@EnableDiscoveryClient
public class UIMBoot {

    public static void main(String[] args) {
        SpringApplication.run(UIMBoot.class, args);
    }
}
