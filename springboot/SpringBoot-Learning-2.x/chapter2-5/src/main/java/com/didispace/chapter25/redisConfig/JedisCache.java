package com.didispace.chapter25.redisConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.naming.KeyNamingStrategy;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

@Component
@Slf4j
public class JedisCache {

    @Autowired
    private JedisPool jedisPool;


/**
 * <p>
 * 通过key获取储存在redis中的value
 * </p>
 * <p>
 * 并释放连接
 * </p>
 */

 public  String  getJedisValue(final  String key,int  indexDB){
         Jedis  jedis=null;
         String  value=null;

         try {
             jedis=jedisPool.getResource();
             jedis.select(indexDB);
             value=jedis.get(key);
         }catch (Exception e){
             log.error(e.getMessage());
         }finally {
             closeJedisClient(jedis);
         }
         return  value;
     }



    /**
     * <p>
     * 通过key获取储存在redis中的value
     * </p>
     * <p>
     * 并释放连接
     * </p>
     *
     * @param key
     * @param indexdb 选择redis库 0-15
     * @return 成功返回value 失败返回null
     */
    public byte[] get(byte[] key,int indexdb) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            value = jedis.get(key);
        } catch (Exception e) {
            log.error(e.getMessage()+"30");
        } finally {
            closeJedisClient(jedis);
        }
        return value;
    }


    /**
     * <p>
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * </p>
     *
     * @param key
     * @param value
     *            过期时间，单位：秒
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long expire(String key, int value, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);

            return jedis.expire(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            closeJedisClient(jedis);
        }

    }




    /**
     * 关闭连接
     * @param jedis
     */
     private void closeJedisClient(Jedis jedis){
         if(jedis !=null ){
             jedis.close();
         }

 }






}
