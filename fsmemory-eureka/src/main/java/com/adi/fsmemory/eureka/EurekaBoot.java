package com.adi.fsmemory.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaBoot {

    public static void main(String[] args) {
        SpringApplication.run(EurekaBoot.class, args);
    }
}
