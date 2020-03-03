package com.ribbon.service.service2.Config;


import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


public class RibbonConfig {

    public RestTemplate  restTemplate(){
        return   new RestTemplate();
    }

}
