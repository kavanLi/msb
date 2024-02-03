package com.msb.rediscluster.access;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRedisString {

    @Autowired
    private RedisString redisString;

    @Test
    void testSet(){
        System.out.println(redisString.set("test","Hello Java"));
    }

    @Test
    void testGet(){
        System.out.println(redisString.get("test"));
    }
}
