package com.msb.msbdemo;

import java.sql.SQLOutput;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建任务T2 FutureTask
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());

        // 创建任务T1 FutureTask
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

        // 线程1 执行T2任务
        Thread t1 = new Thread(ft2);

        t1.start();
        //线程2 执行任务T1
        Thread t2 = new Thread(ft1);
        t2.start();;
        // 等待返回结果
        System.out.println(ft1.get());
    }
}


// T2任务 洗茶壶、洗茶杯、拿茶叶
class T2Task implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("T2 洗茶壶。。。。");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T2 洗茶杯。。。。。");
        TimeUnit.SECONDS.sleep(2);

        System.out.println("T2 拿茶叶。。。。。。");
        TimeUnit.SECONDS.sleep(1);
        return "铁观音";
    }
}
// T1 任务 洗水壶、烧开水、泡茶
class T1Task implements Callable<String>{


    FutureTask<String> ft2;
    public T1Task(FutureTask<String> ft2){
        this.ft2 = ft2;
    }

    @Override
    public String call() throws Exception {
        System.out.println("T1 洗水壶。。。。。");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T1 烧开水。。。。。。。");
        TimeUnit.SECONDS.sleep(2);

        // 获取T2线程
        String res = ft2.get();
        System.out.println("T1 拿到茶叶。。。。" + res);
        System.out.println("T1 开始泡茶。。。。");


        return "喝茶" + res;
    }
}