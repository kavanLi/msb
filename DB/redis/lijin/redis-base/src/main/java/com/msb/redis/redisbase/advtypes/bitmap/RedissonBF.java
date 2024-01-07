package com.msb.redis.redisbase.advtypes.bitmap;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
/*Redisson底层基于位图实现了一个布隆过滤器，使用非常方便*/
public class RedissonBF {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        //构造Redisson
        RedissonClient redisson = Redisson.create(config);

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("phoneList");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(100000000L,0.03);
        //将号码10081~10086插入到布隆过滤器中
        bloomFilter.add("10081");
        bloomFilter.add("10082");
        bloomFilter.add("10083");
        bloomFilter.add("10084");
        bloomFilter.add("10085");
        bloomFilter.add("10086");

        //判断下面号码是否在布隆过滤器中
        System.out.println("123456:BF--"+bloomFilter.contains("123456"));//false
        System.out.println("10086:BF--"+bloomFilter.contains("10086"));//true
        System.out.println("10084:BF--"+bloomFilter.contains("10084"));//true

    }
}
