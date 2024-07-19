package com.msb.caffeine.caffeineDemo;

import com.github.benmanes.caffeine.cache.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineTestExpireType {
    public static void main(String[] args) throws Exception{
        //ExpireMaxType();
        // ExpireWeigherType();
        //ExpireAfterAccess();
        //ExpireAfterWrite();
        //MyExpire();
        //ExpireSoft();
        ExpireWeak();
    }
    //数量驱逐策略
    public static void ExpireMaxType() throws Exception{
        //Caffeine 会有一个异步线程来专门负责清除缓存
        Cache<String, String> cache = Caffeine.newBuilder()
                //将最大数量设置为一
                .maximumSize(1)
                .expireAfterAccess(3L, TimeUnit.SECONDS)
                .build();
        cache.put("name","张三");
        cache.put("age","18");
        System.out.println(cache.getIfPresent("name"));
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println(cache.getIfPresent("name"));
        System.out.println(cache.getIfPresent("age"));

    }

    //权重驱逐策略:存入缓存的每个元素都要有一个权重值，当缓存中所有元素的权重值超过最大权重时，就会触发异步清除。
    public static void ExpireWeigherType() throws Exception{
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumWeight(100)
                .weigher(((key, value) -> {
                    System.out.println("权重处理，key="+key+" value="+value);
                    //这里直接返回一个固定的权重，真实开发会有一些业务的运算
                    if(key.equals("age")){
                        return 30;
                    }
                    return 50;
                }))
                .expireAfterAccess(3L, TimeUnit.SECONDS)
                .build();
        cache.put("name","张三");
        cache.put("age","18");

        cache.put("sex","男");
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println(cache.getIfPresent("name"));
        System.out.println(cache.getIfPresent("age"));
        System.out.println(cache.getIfPresent("sex"));
    }

    //时间驱逐策略--最后一次读
    public static void ExpireAfterAccess() throws Exception{
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1L,TimeUnit.SECONDS)
                .build();
        cache.put("name","张三");
        for (int i = 0; i < 10; i++) {
            System.out.println("第"+i+"次读："+cache.getIfPresent("name"));
            TimeUnit.SECONDS.sleep(2);
        }
    }

    //时间驱逐策略--最后一次写
    public static void ExpireAfterWrite() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1L,TimeUnit.SECONDS)
                .build();
        cache.put("name","张三");
        for (int i = 0; i < 10; i++) {
            System.out.println("第"+i+"次读："+cache.getIfPresent("name"));
            TimeUnit.SECONDS.sleep(1);
        }
    }

    //自定义失效策略
    public static void MyExpire() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfter(new MyExpire())
                .build();
        cache.put("name", "张三");
        for (int i = 0; i < 10; i++) {
            System.out.println("第" + i + "次读：" + cache.getIfPresent("name"));
            TimeUnit.SECONDS.sleep(1);
        }
    }

    //基于引用驱逐策略--软引用:-Xms20m -Xmx20m  堆空间的大小限定在20M
    public static void ExpireSoft() throws InterruptedException {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .softValues()
                .build();

        cache.put("name",new SoftReference<>("张三"));
        System.out.println("第1次读："+cache.getIfPresent("name"));
        List<byte[]> list = new LinkedList<>();
        try {
            for(int i=0;i<100;i++) {
                list.add(new byte[1024*1024*1]); //1M的对象
            }
        } catch (Throwable e) {
            //抛出了OOM异常时
            //TimeUnit.SECONDS.sleep(1);
            System.out.println("OOM时读："+cache.getIfPresent("name"));
            System.out.println("Exception*************"+e.toString());
        }
    }

    //基于引用驱逐策略--弱引用
    public static void ExpireWeak() throws InterruptedException {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .weakValues()
                .build();
        cache.put("name",new WeakReference<>("张三"));

        System.out.println("第1次读："+cache.getIfPresent("name"));
        System.gc();//进行一次GC垃圾回收
        System.out.println("GC后读："+cache.getIfPresent("name"));
    }

}

