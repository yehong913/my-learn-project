package com.glaway.ids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.glaway.ids.*","com.alibaba.druid"})
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.glaway.*"})
@EnableTransactionManagement
@EntityScan(basePackages = {"com.glaway.*"})
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableScheduling
public class IDSWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IDSWebApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(IDSWebApplication.class, args);
    }
}

