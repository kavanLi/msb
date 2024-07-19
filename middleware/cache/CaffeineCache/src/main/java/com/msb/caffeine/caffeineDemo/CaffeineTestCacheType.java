package com.msb.caffeine.caffeineDemo;

import com.github.benmanes.caffeine.cache.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
//原生的操作
@Slf4j
public class CaffeineTestCacheType {
    public static void main(String[] args) throws Exception{






        Cache();
         //CacheExpire();
        //LoadingCache();
        AsyncLoadingCache();
    }
    //基本使用
    public static void Cache() throws Exception{
        Cache<String, String> cache = Caffeine.newBuilder()//构建一个新的Caffeine实例
                .maximumSize(100)//设置缓存中保存的最大数量
                .expireAfterAccess(3L, TimeUnit.SECONDS)//如无访问则3秒后失效
                .build();//构建Cache接口实例

        cache.put("mca","www.mashibing.com");//设置缓存项
        cache.put("baidu","www.baidu.com");//设置缓存项
        cache.put("spring","www.spring.io");//设置缓存项

        log.info("获取缓存[getIfPresent]:mca={}",cache.getIfPresent("mca"));//获取数据
        TimeUnit.SECONDS.sleep(5);//休眠5秒

        log.info("获取缓存[getIfPresent]:mca={}",cache.getIfPresent("mca"));//获取数据

    }

    //基本使用-过期数据的同步加载1
    public static void CacheExpire() throws Exception{
        Cache<String, String> cache = Caffeine.newBuilder()//构建一个新的Caffeine实例
                .maximumSize(100)//设置缓存中保存的最大数量
                .expireAfterAccess(3L, TimeUnit.SECONDS)//如无访问则3秒后失效
                .build();//构建Cache接口实例
        cache.put("mca","www.mashibing.com");//设置缓存项
        cache.put("baidu","www.baidu.com");//设置缓存项
        cache.put("spring","www.spring.io");//设置缓存项
        TimeUnit.SECONDS.sleep(5);//休眠5秒
        log.info("获取缓存[getIfPresent]:baidu={}",cache.getIfPresent("baidu"));//获取数据

        log.info("获取缓存[get]获取缓存:baidu={}",cache.get("baidu",(key)->{
            log.info("进入[失效处理]函数");
            try {
                TimeUnit.SECONDS.sleep(3);//休眠3秒
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("[失效处理]:mca={}",cache.getIfPresent("mca"));//失效处理
            return key.toUpperCase();
        }));

        log.info("经过失效处理之后[getIfPresent]:mca={}",cache.getIfPresent("mca"));//获取数据
        log.info("经过失效处理之后[getIfPresent]:baidu={}",cache.getIfPresent("baidu"));//获取数据
    }
    //基本使用-过期数据的同步加载2
    public static void LoadingCache() throws Exception{
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)//设置缓存中保存的最大数量
                .expireAfterAccess(3L, TimeUnit.SECONDS)//如无访问则3秒后失效
                .build(new CacheLoader<String, String>() {
                    @Override
                    public  String load( String key) throws Exception {
                        log.info("正在重新加载数据...");
                        TimeUnit.SECONDS.sleep(1);
                        return key.toUpperCase();
                    }

                });

        cache.put("mca","www.mashibing.com");//设置缓存项
        cache.put("baidu","www.baidu.com");//设置缓存项
        cache.put("spring","www.spring.io");//设置缓存项

        TimeUnit.SECONDS.sleep(5);

        //创建key的列表，通过cache.getAll()拿到所有key对应的值
        ArrayList<String> keys = new ArrayList<>();
        keys.add("mca");
        keys.add("baidu");
        keys.add("spring");
        //拿到keys对应缓存的值
        Map<String, String> map = cache.getAll(keys);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            log.info("缓存的键:{}、缓存值：{}",entry.getKey(),entry.getValue());//获取数据
        }
        log.info("LoadingCache 方法结束");

    }
    //基本使用-过期数据的异步加载
    public static void AsyncLoadingCache () throws Exception{
        AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)//设置缓存中保存的最大数量
                .expireAfterAccess(3L, TimeUnit.SECONDS)
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
        cache.put("mca",CompletableFuture.completedFuture("www.mashibing.com"));//设置缓存项
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
        log.info("AsyncLoadingCache 方法结束");
    }

}
