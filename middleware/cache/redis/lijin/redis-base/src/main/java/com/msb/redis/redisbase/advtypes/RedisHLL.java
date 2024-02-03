package com.msb.redis.redisbase.advtypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisHLL {

    public final static String RS_HLL_NS = "rhll:";

    @Autowired
    private JedisPool jedisPool;


    public void count() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            for(int i=0;i<10000;i++){
                jedis.pfadd(RS_HLL_NS+"countest","user"+i);
            }
            long total = jedis.pfcount(RS_HLL_NS+"countest");
            System.out.println("实际次数:" + 10000 + "，HyperLogLog统计次数:"+total);
        } catch (Exception e) {

        } finally {
            jedis.close();
        }
    }
}
