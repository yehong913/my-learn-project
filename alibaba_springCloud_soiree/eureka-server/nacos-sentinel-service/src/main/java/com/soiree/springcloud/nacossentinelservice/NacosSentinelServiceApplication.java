package com.soiree.springcloud.nacossentinelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosSentinelServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosSentinelServiceApplication.class, args);
    }

}
