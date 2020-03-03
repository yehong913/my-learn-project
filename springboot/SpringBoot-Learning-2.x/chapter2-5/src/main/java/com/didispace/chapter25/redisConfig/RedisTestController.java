package com.didispace.chapter25.redisConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    @Autowired
    private RedisUtils redisUtils;


    @RequestMapping("RedisGet")
    @ResponseBody
    public String get(@RequestParam("key") String key){
        return redisUtils.get(key);
    }


    @RequestMapping("RedisSet")
    @ResponseBody
    public boolean set(@RequestParam("key") String key,
                       @RequestParam("value") String value){
        return redisUtils.set(key,value);
    }



    @RequestMapping("RedisGetAndSet")
    @ResponseBody
    public boolean RedisGetAndSet(@RequestParam("key") String key,
                       @RequestParam("value") String value){
        return redisUtils.getAndSet(key,value);
    }

    @RequestMapping("RedisDelete")
    @ResponseBody
    public boolean delete(@RequestParam("key") String key){
        return redisUtils.delete(key);
    }

}
