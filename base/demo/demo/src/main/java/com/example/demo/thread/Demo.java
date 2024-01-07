package com.example.demo.thread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: kavanLi-R7000
 * @create: 2023-09-09 18:57
 * To change this template use File | Settings | File and Code Templates.
 */
public class Demo {
    /* fields -------------------------------------------------------------- */
    public static AtomicInteger count = new AtomicInteger(0);

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */
    static ThreadLocal tl1 = new ThreadLocal();
    static ThreadLocal tl2 = new ThreadLocal();
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    static int[] nums = new int[1_000_000];

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (int) ((Math.random()) * 1000);
        }
    }

    static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    static CountDownLatch countDownLatch = new CountDownLatch(3);


    public static void main(String[] args) throws InterruptedException, IOException {
        //CompletableFuture <String> firstTask = CompletableFuture.supplyAsync(() -> {
        //    return UUID.randomUUID().toString();
        //}).(() -> {
        //
        //});
        //firstTask.join();
        //firstTask.get();
        ExecutorService executor = Executors.newFixedThreadPool(10);



        for (int i = 0; i < 10000; i++) {
            System.out.println(i++);
        }


    }

    private static void a() {
        System.out.println("A");
        sleep(1000);
        System.out.println("A");
        countDownLatch.countDown();
    }

    private static void b() {
        System.out.println("B");
        sleep(1555);
        System.out.println("B");
        countDownLatch.countDown();
    }

    private static void c() {
        System.out.println("C");
        sleep(2222);
        System.out.println("C");
        countDownLatch.countDown();
    }

    private static void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void newThreadPool() {
        ExecutorService executorService1 = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 200; i++) {
            int finalI = i;
            executorService1.execute(() -> {
                System.out.println(finalI);
            });
        }
        executorService1.shutdown();
        ExecutorService executorService111 = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 200; i++) {
            int finalI = i;
            executorService111.execute(() -> {
                System.out.println(finalI);
            });
        }
        executorService111.shutdown();

    }

    /* private methods ----------------------------------------------------- */

    /* getters/setters ----------------------------------------------------- */

}

