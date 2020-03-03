package com.eureka.admin.ckient2.adminclient2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Adminclient2Application {

    public static void main(String[] args) {
        SpringApplication.run(Adminclient2Application.class, args);
    }

}
