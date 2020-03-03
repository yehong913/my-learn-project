package com.admin.server.soiree.eurekaadmin.com.main;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
public class EurekaadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaadminApplication.class, args);
    }

}
