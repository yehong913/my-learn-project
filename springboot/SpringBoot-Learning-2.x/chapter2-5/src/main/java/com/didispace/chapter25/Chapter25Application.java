package com.didispace.chapter25;

import com.didispace.chapter25.ThreadTest.ThreadLocalExample.HttpFilter;
import com.didispace.chapter25.ThreadTest.ThreadLocalExample.HttpInterceptorExample;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableSwagger2Doc
@SpringBootApplication
public class Chapter25Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Chapter25Application.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptorExample()).addPathPatterns("/*");
    }

    @Bean
    public FilterRegistrationBean httpFilter(){
        FilterRegistrationBean filterRegistrationBean= new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new HttpFilter());
        filterRegistrationBean.addUrlPatterns("/threadLocal/*");
        return filterRegistrationBean;
    }

}
