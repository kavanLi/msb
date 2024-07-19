package com.example.demo;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.property.AppIdConfig;
import com.example.demo.config.property.AppIdConfigProperties;
import com.example.demo.config.property.ListProperties;
import com.example.demo.config.property.UserEntity;
import com.example.demo.exception.BaseException;
import com.example.demo.service.AsyncService;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootTest
@EnableAsync
class DemoApplicationTests {

    @Autowired
    private ListProperties listProperties;

    @Autowired
    private AppIdConfigProperties appIdConfigProperties;

    @Resource
    private AsyncService asyncService;

    //public static Object lock = new Object();
    public static int i = 100;
    public static Lock lock = new ReentrantLock(true);
    //public static Condition condition = lock.newCondition();

    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75f, true);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return super.size() > capacity;
        }
    }

    @Test
    public void retryTest() {
        System.out.println(123);
        // 配置重试策略
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(3))
                .retryExceptions(BaseException.class)
                .build();

        Retry retry = Retry.of("wxPhoneNumberRetry", config);
        AtomicReference <Integer> ab = new AtomicReference <>(1);
        // 定义需要重试的操作
        Supplier <JSONObject> retryableSupplier = Retry.decorateSupplier(retry, () -> {
            // 获取缓存中的 access token

            if (ab.get() < 3) {
                ab.set(ab.get() + 1);
                throw new BaseException("Token error, will retry");
            }
            // 解析响应
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("123", "312");

            // 如果没有错误，返回结果
            return jsonObject;
        });

        // 使用 Try 执行重试操作
        JSONObject result = Try.ofSupplier(retryableSupplier)
                .recover(throwable -> {
                    System.out.println(123);
                    if (throwable instanceof BaseException) {
                        throw (BaseException) throwable;
                    } else {
                        throw new RuntimeException(throwable);
                    }
                }).get();

        System.out.println(321);
    }

    private static void showBit(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println("\n" + (~(Integer.MIN_VALUE + 1) + 1));
    }

    @Test
    void contextLoads() {
        Stack <Integer> stack = new Stack <>();
        stack.push(3);
        stack.push(2);
        stack.push(1);
        stack.pop();
        stack.pop();
        stack.pop();

        List <UserEntity> users = listProperties.getUsers();
        List <AppIdConfig> appIdConfigs = appIdConfigProperties.getApp();
    }

    @Test
    void test1() {
        System.out.println("start");
        asyncService.processCompletableFuture("6666");
        //try {
        //    Thread.sleep(5000);
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
        System.out.println("end");

    }

    @Test
    void test2() {
    }

    @Test
    void test3() {
    }

    @Test
    void test4() {
    }

    @Test
    void test5() {
    }

    @Test
    void test6() {
    }

    @Test
    void test7() {
    }
}
