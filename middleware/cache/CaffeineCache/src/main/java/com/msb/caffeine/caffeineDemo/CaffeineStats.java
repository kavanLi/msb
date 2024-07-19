package com.msb.caffeine.caffeineDemo;

import com.github.benmanes.caffeine.*;
import com.github.benmanes.caffeine.cache.*;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineStats {
    public static void main(String[] args) throws Exception{
        CacheStats();
        //AsyncLoadingCache();
        MyStatsCounter();
    }

    public static void CacheStats () throws Exception{
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(2)
                .recordStats() //开启统计功能
                .expireAfterAccess(200L,TimeUnit.SECONDS)
                .build();
        cache.put("name","张三");
        cache.put("sex","男");
        cache.put("age","18");
        //设置的key有些是不存在的,通过这些不存在的进行非命中操作
        String[] keys = new String[]{"name","age","sex","phone","school"};
        for (int i = 0; i < 1000; i++) {
            cache.getIfPresent(keys[new Random().nextInt(keys.length)]);
        }
        CacheStats stats = cache.stats();
        System.out.println("用户请求查询总次数："+stats.requestCount());
        System.out.println("命中个数："+stats.hitCount());
        System.out.println("命中率："+stats.hitRate());
        System.out.println("未命中次数："+stats.missCount());
        System.out.println("未命中率："+stats.missRate());

        System.out.println("加载次数："+stats.loadCount());
        System.out.println("总共加载时间："+stats.totalLoadTime());
        System.out.println("平均加载时间（单位-纳秒）："+stats.averageLoadPenalty ());
        System.out.println("加载失败率："+stats.loadFailureRate()); //加载失败率，= 总共加载失败次数 / 总共加载次数
        System.out.println("加载失败次数："+stats.loadFailureCount());
        System.out.println("加载成功次数："+stats.loadSuccessCount());

        System.out.println("被淘汰出缓存的数据总个数："+stats.evictionCount());
        System.out.println("被淘汰出缓存的那些数据的总权重："+stats.evictionWeight());


    }

    public static void AsyncLoadingCache () throws Exception{
        AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)//设置缓存中保存的最大数量
                .expireAfterAccess(3L, TimeUnit.SECONDS)
                .recordStats()
                .buildAsync(new CacheLoader<String, String>() {
                    @Override
                    public  String load( String key) throws Exception {
                        log.info("正在重新加载数据...");
                        TimeUnit.SECONDS.sleep(1);
                        return key.toUpperCase();
                    }
                });
        //使用了异步的缓存之后，缓存的值都是被CompletableFuture给包裹起来的
        //所以在追加缓存和得到缓存的时候要通过操作CompletableFuture来进行
        cache.put("mca", CompletableFuture.completedFuture("www.mashibing.com"));//设置缓存项
        cache.put("baidu",CompletableFuture.completedFuture("www.baidu.com"));//设置缓存项
        cache.put("spring",CompletableFuture.completedFuture("www.spring.io"));//设置缓存项

        TimeUnit.SECONDS.sleep(5);

        //创建key的列表，通过cache.getAll()拿到所有key对应的值
        ArrayList<String> keys = new ArrayList<>();
        keys.add("mca");
        keys.add("baidu");
        keys.add("spring");
        //拿到keys对应缓存的值
        Map<String, String> map = cache.getAll(keys).get();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            log.info("缓存的键:{}、缓存值：{}",entry.getKey(),entry.getValue());//获取数据
        }
        CacheStats stats = cache.synchronous().stats();
        System.out.println("加载次数："+stats.loadCount());
        System.out.println("总共加载时间："+stats.totalLoadTime());
        System.out.println("平均加载时间（单位-纳秒）："+stats.averageLoadPenalty ());
        System.out.println("加载成功次数："+stats.loadSuccessCount());
        System.out.println("加载失败率："+stats.loadFailureRate()); //加载失败率，= 总共加载失败次数 / 总共加载次数
        System.out.println("加载失败次数："+stats.loadFailureCount());

        System.out.println("被淘汰出缓存的数据总个数："+stats.evictionCount());
        System.out.println("被淘汰出缓存的那些数据的总权重："+stats.evictionWeight());

        log.info("AsyncLoadingCache 方法结束");
    }

    public static void MyStatsCounter() throws Exception{
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .recordStats(()->new MyStatsCounter())
                .expireAfterAccess(200L,TimeUnit.MILLISECONDS)
                .build();
        cache.put("name","张三");
        System.out.println(cache.getIfPresent("name"));
    }

}
