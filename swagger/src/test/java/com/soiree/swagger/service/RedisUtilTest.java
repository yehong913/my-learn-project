package com.soiree.swagger.service;

import com.soiree.swagger.SwaggerApplication;
import com.soiree.swagger.common.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author soiree
 * @version 1.0
 * @date 2020/3/9 16:46
 * @desp
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SwaggerApplication.class})
public class RedisUtilTest {
    @Resource
    private RedisUtil  redisUtil;

    @Test
    public  void  test01(){
        redisUtil.set("test01","vcalue1");
        String value= (String) redisUtil.get("test01");
        log.info(value);

    }



}
