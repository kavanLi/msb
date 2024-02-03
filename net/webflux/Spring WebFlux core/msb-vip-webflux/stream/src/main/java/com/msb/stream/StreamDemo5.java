package com.msb.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class StreamDemo5 {
    public static void main(String[] args) {
        //1、调用 顺序进行调用
        // IntStream.range(1,100).peek(StreamDemo5::debug).count();
        //2、调用 paralled产生一个并行流
          //IntStream.range(1,100).parallel().peek(StreamDemo5::debug).count();

        //3、现在要实现一个这样的效果： 先并行，再串行
        // 多次调用 parallel / sequential 以最后一个次为准
//        IntStream.range(1,100)
//                // 调用paralled产生并行流
//                .parallel().peek(StreamDemo5::debug)
//                // 调用sequential 产生并行流
//                .sequential().peek(StreamDemo5::debug2)
//                .count();

        // 4.并行流使用的线程池：ForkJoinPool.commonPool
////        IntStream.range(1,100).parallel().peek(StreamDemo5::debug).count();
        // 5、我们现在并行流都是用的同一个线程池，那就会有一下问题：
        //  多个并行任务用同一个线程池，就会有排队等待的情况，这时候我们就需要自己创建线程池
        // 防止任务被阻塞
         ForkJoinPool pool = new ForkJoinPool(20);
        pool.submit(() -> IntStream.range(1,100).parallel().peek(StreamDemo5::debug).count());
        // 处理完毕线程池会自动关闭
        pool.shutdown();
        // 我们实现将自己的任务放到自己并行线程池里面，我们测试一下
        // 发现控制台并没有任何日志打印出来
        // 这是因为主线程已经停了，我们线程池是守护线程的关系，所以他也自动退出了，那我们需要让我们的主线程不要退出

        synchronized (pool){
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 运行之后发现用的是我们自己的线程池 ForkJoinPool-1
    }
    public static void debug(int i){
        System.out.println(Thread.currentThread().getName() + "  debug " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void debug2(int i){
        System.out.println("  debug2 " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
