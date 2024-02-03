package com.msb.redis.redismq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class ListVer{
    public final static String RS_LIST_MQ_NS = "rlm:";

    @Autowired
    private JedisPool jedisPool;

    /*消费者接受消息*/
    public List<String> get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(0,RS_LIST_MQ_NS +key);
        } catch (Exception e) {
            throw new RuntimeException("接受消息失败！");
        } finally {
            jedis.close();
        }
    }

    /*生产者发送消息*/
    public void put(String key, String message) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(RS_LIST_MQ_NS+key,message);
        } catch (Exception e) {
            throw new RuntimeException("发送消息失败！");
        } finally {
            jedis.close();
        }
    }
}
