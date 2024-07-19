package com.msb.caffeine.caffeineDemo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;
//清除、更新异步监听
public class CaffeineRemoveListener {
    public static void main(String[] args) throws Exception {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(2)
                .removalListener(((key, value, cause) -> System.out.println("键："+key+" 值："+value+" 清除原因："+cause)))
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build();
        cache.put("name","张三");
        cache.put("sex","男");
        cache.put("age","18");
        TimeUnit.SECONDS.sleep(2);
        cache.put("name2","张三");
        cache.put("age2","18");
        cache.invalidate("age2");
        TimeUnit.SECONDS.sleep(10);
    }
}
