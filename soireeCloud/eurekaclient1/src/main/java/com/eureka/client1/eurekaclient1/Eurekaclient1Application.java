package com.eureka.client1.eurekaclient1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Eurekaclient1Application {

    public static void main(String[] args) {
        SpringApplication.run(Eurekaclient1Application.class, args);
    }

}
