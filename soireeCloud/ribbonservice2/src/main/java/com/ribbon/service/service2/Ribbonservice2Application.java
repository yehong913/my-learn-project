package com.ribbon.service.service2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class Ribbonservice2Application {

    public static void main(String[] args) {
        SpringApplication.run(Ribbonservice2Application.class, args);
    }
    // RestTemplate注入springBoot容器管理
    @Bean
    //启用本地服务负载均衡，使用服务名URL调用要加此注解
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
