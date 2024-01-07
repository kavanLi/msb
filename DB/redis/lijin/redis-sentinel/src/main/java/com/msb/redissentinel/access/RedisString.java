package com.msb.redissentinel.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 操作字符串类型
 */
@Component
public class RedisString {

    public final static String RS_STR_NS = "rs:";

    @Autowired
    private JedisSentinelPool jedisSentinelPool;

    /**
     * 向Redis中存值，永久有效
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.set(RS_STR_NS +key, value);
        } catch (Exception e) {
            throw new RuntimeException("向Redis中存值失败！");
        } finally {
            jedis.close();
        }
    }

    /**
     * 批量向Redis中存值，永久有效
     */
    public String msetRaw(String... keysvalues) {
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.mset(keysvalues);
        } catch (Exception e) {
            throw new RuntimeException("批量向Redis中存值失败！");
        } finally {
            jedis.close();
        }
    }

    /**
     * 根据传入Key获取指定Value
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.get(RS_STR_NS +key);
        } catch (Exception e) {
            throw new RuntimeException("获取Redis值失败！");
        } finally {
            jedis.close();
        }
    }

}
