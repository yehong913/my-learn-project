//package com.glaway.ids.common.constant;
//
//import feign.Logger;
//import feign.codec.Encoder;
//import feign.form.spring.SpringFormEncoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.Scope;
//
///**
// * Created by LHR on 2019/8/21.
// */
//@Configuration
//public class FeignMultipartSupportConfig {
//
//    @Bean
//    @Primary
//    @Scope("prototype")
//    public Encoder multipartFormEncoder(){
//        return new SpringFormEncoder();
//    }
//
//    @Bean
//    public feign.Logger.Level multipartLoggerLevel(){
//        return Logger.Level.FULL;
//    }
//}
