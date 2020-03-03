package com.didispace.chapter25.redisConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String,String>  redisTemplate;

    /**
     * 取出
     * @param key
     * @return
     */
    public  String  get(final  String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 存
     * @param key
     * @param value
     * @return
     */
    public   boolean set(final  String key,String value){
        boolean result=false;
         try{
             redisTemplate.opsForValue().set(key,value);
             return true;
         }catch (Exception e){
             e.printStackTrace();
         }
         return   result;
    }


    public   boolean getAndSet(final  String key,String value){
        boolean result=false;
        try{
            redisTemplate.opsForValue().getAndSet(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return   result;
    }

    public   boolean delete(final  String key){
        boolean result=false;
        try{
            redisTemplate.delete(key);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return   result;
    }



}

