package com.msb.msbdemo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTest {
    public static void main(String[] args) {
        // 任务1 ：洗水壶 -》 烧开水
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1 洗水壶。。。。");
            sleep(1,TimeUnit.SECONDS);

            System.out.println("T1 烧开水。。。。。");
            sleep(2,TimeUnit.SECONDS);
        });
        // 任务2： 洗茶壶 -》 洗茶杯 -》 拿茶叶
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2 洗茶壶。。。。");
            sleep(1,TimeUnit.SECONDS);

            System.out.println("T2 洗茶杯。。。。");
            sleep(2,TimeUnit.SECONDS);

            System.out.println("T2 拿茶叶。。。。。");
            sleep(1,TimeUnit.SECONDS);
            return "铁观音";
        });
        // 任务3： 任务1和任务2完成后执行泡茶
        CompletableFuture<Object> f3 = f1.thenCombine(f2, (__, tf) -> {
            System.out.println("T1 拿到茶叶" + tf);
            System.out.println("T1 开始泡茶");
            return "喝茶 ：" + tf;
        });
        // 等待任务3的执行结果
        System.out.println(f3.join());
    }


    static void sleep(int t ,TimeUnit u){
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
