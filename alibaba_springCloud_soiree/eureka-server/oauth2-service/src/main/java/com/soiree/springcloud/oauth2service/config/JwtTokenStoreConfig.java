package com.soiree.springcloud.oauth2service.config;


import com.soiree.springcloud.oauth2service.component.JwtTokenEnhancer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.util.FileCopyUtils;


import java.io.IOException;


/**
 * 使用Jwt存储token的配置
 * Created by macro on 2019/10/8.
 */
@Configuration
public class JwtTokenStoreConfig {
    @Bean
    public TokenStore jwtTokenStore() throws IOException {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }



    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws IOException {
       JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
       /* KeyStoreKeyFactory storeKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("hellxz-jwt.jks"), "hellxzTest".toCharArray());
        accessTokenConverter.setKeyPair(storeKeyFactory.getKeyPair("hellxz-jwt"));;//配置JWT使用的秘钥*/
/*
        Resource resource = new ClassPathResource("public.cert");
        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        accessTokenConverter.setVerifierKey(publicKey);*/
        accessTokenConverter.setSigningKey("test_key");
        return accessTokenConverter;
    }

    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }






}
