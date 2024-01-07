package com.msb.redis.redisbase.basetypes;

import com.msb.redis.redisbase.advtypes.bitmap.RedisBloomFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRedisBloomFilter {

    private static final int DAY_SEC = 60 * 60 * 24;

    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @Test
    public void testInsert() throws Exception {
       // System.out.println(redisBloomFilter);
        redisBloomFilter.insert("bloom:user", "20210001", DAY_SEC);
        redisBloomFilter.insert("bloom:user", "20210002", DAY_SEC);
        redisBloomFilter.insert("bloom:user", "20210003", DAY_SEC);
        redisBloomFilter.insert("bloom:user", "20210004", DAY_SEC);
        redisBloomFilter.insert("bloom:user", "20210005", DAY_SEC);
    }

    @Test
    public void testMayExist() throws Exception {
        System.out.println(redisBloomFilter.mayExist("bloom:user", "20210001"));
        System.out.println(redisBloomFilter.mayExist("bloom:user", "20210002"));
        System.out.println(redisBloomFilter.mayExist("bloom:user", "20210003"));


        System.out.println(redisBloomFilter.mayExist("bloom:user", "20211001"));
    }

}
