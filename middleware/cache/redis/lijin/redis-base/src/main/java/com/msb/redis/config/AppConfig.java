package com.msb.redis.config;

import com.msb.redis.redisbase.advtypes.bitmap.RedisBloomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /*可以放入配置文件或者应用自行设置*/
    private static final int NUM_APPROX_ELEMENTS = 3000;
    private static final double FPP = 0.03;

    @Bean
    public RedisBloomFilter getRedisBloomFilter() {
        RedisBloomFilter redisBloomFilter = new RedisBloomFilter();
        redisBloomFilter.init(NUM_APPROX_ELEMENTS,FPP);
        return redisBloomFilter;
    }
}
