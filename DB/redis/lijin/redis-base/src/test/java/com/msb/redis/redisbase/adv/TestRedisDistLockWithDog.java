package com.msb.redis.redisbase.adv;


import com.msb.redis.lock.rdl.RedisDistLockWithDog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class TestRedisDistLockWithDog {

    @Autowired
    private RedisDistLockWithDog redisDistLockWithDog;
    private int count = 0;


    @Test
    public void testLockWithDog() throws InterruptedException {
        int clientCount =3;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
        ExecutorService executorService = Executors.newFixedThreadPool(clientCount);
        for (int i = 0;i<clientCount;i++){
            executorService.execute(() -> {
                try {
                    redisDistLockWithDog.lock(); //锁的有效时间1秒
                    System.out.println(Thread.currentThread().getName()+"准备进行累加。");
                    Thread.sleep(2000);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    redisDistLockWithDog.unlock();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println(count);
    }

    @Test
    public void testTryLock2() {
        int clientCount =1000;
        for (int i = 0;i<clientCount;i++) {
            if (redisDistLockWithDog.tryLock()) {
                System.out.println("已获得锁！");
                redisDistLockWithDog.unlock();
            } else {
                System.out.println("未能获得锁！");
            }
        }
    }

}
