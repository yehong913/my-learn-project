package com.soiree.swagger.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.soiree.swagger.mapper")
public class MyBatisConfig {
}
