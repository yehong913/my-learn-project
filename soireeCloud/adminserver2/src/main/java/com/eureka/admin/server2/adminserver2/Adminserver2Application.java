package com.eureka.admin.server2.adminserver2;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
public class Adminserver2Application {

    public static void main(String[] args) {
        SpringApplication.run(Adminserver2Application.class, args);
    }

}
