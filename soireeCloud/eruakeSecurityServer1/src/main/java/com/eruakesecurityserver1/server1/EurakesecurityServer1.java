package com.eruakesecurityserver1.server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurakesecurityServer1 {

    public static void main(String[] args) {
        SpringApplication.run(EurakesecurityServer1.class, args);
    }

}
