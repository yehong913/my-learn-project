package com.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Ribbonservice1Application {

    public static void main(String[] args) {
        SpringApplication.run(Ribbonservice1Application.class, args);
    }

}
