package com.msb.redis.redisbase.adv;


import com.msb.redis.adv.RedisLua;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRedisLua {

    @Autowired
    private RedisLua redisLua;

    @Test
    public void testLoad() {
        System.out.println(redisLua.loadScripts());
    }

    @Test
    public void tesIpLimitFlow() {
        System.out.println(redisLua.ipLimitFlow("localhost"));
    }

}
