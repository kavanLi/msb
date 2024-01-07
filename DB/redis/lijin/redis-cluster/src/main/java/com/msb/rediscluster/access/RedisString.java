package com.msb.rediscluster.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 操作字符串类型
 */
@Component
public class RedisString {

    public final static String RS_STR_NS = "rs:";

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 向Redis中存值，永久有效
     */
    public String set(String key, String value) {
        try {
            return jedisCluster.set(RS_STR_NS +key, value);
        } catch (Exception e) {
            throw new RuntimeException("向Redis中存值失败！");
        } finally {
        }
    }

    /**
     * 根据传入Key获取指定Value
     */
    public String get(String key) {
        try {
            return jedisCluster.get(RS_STR_NS +key);
        } catch (Exception e) {
            throw new RuntimeException("获取Redis值失败！");
        } finally {
        }
    }

}
