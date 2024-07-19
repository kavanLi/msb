package com.msb.caffeine.caffeineDemo;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CaffeineCacheConfig {
    //缓存的最大条数
    public static final Integer CAFFEINE_MAXSIZE =10000;
    //最后一次写入后经过固定时间过期
    public static final Integer CAFFEINE_EXPIRE_TIME =3;
    /**
     * 创建基于Caffeine的Cache Manager
     * @return
     */
//    @Bean("caffeineCacheManager")
//    public CacheManager CaffeineCacheManager() {
//    /*  initialCapacity=[integer]: 初始的缓存空间大小
//        maximumSize=[long]: 缓存的最大条数
//        maximumWeight=[long]: 缓存的最大权重
//        expireAfterAccess=[duration]: 最后一次写入或访问后经过固定时间过期
//        expireAfterWrite=[duration]: 最后一次写入后经过固定时间过期
//        refreshAfterWrite=[duration]: 创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
//        weakKeys: 打开 key 的弱引用
//        weakValues：打开 value 的弱引用
//        softValues：打开 value 的软引用
//        recordStats：开发统计功能
//
//         expireAfterWrite 和 expireAfterAccess 同事存在时，以 expireAfterWrite 为准
//         maximumSize 和 maximumWeight 不可以同时使用
//         weakValues 和 softValues 不可以同时使用
//         */
//
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(Caffeine.newBuilder().recordStats()
//                .expireAfterWrite(CAFFEINE_EXPIRE_TIME, TimeUnit.SECONDS)
//                .maximumSize(CAFFEINE_MAXSIZE));
//        return cacheManager;
//    }
}
