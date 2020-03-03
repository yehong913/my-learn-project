package com.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Ribbonservice11Application {

    public static void main(String[] args) {
        SpringApplication.run(Ribbonservice11Application.class, args);
    }

}
