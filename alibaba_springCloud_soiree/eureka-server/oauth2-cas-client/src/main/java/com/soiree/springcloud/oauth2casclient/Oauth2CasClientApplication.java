package com.soiree.springcloud.oauth2casclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@EnableOAuth2Sso
@SpringBootApplication
public class Oauth2CasClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2CasClientApplication.class, args);
    }

}
