package com.msb.disruptor.heigh.chain;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 构建一个线程池用于提交任务
        ExecutorService es2 = Executors.newFixedThreadPool(5);
        //1、 构建Disruptor
        Disruptor<TradeEvent> disruptor = new Disruptor<TradeEvent>(
                new TradeEventFactory(),
1024* 1024,
            es2,
            ProducerType.SINGLE,
            new BusySpinWaitStrategy()
        );
        // 2、 把消费者设置再Disruptor中的handleEventsWith
        /**
         * 2.1 使用串行操作
         */
//        disruptor.handleEventsWith(new Handler1())
//                .handleEventsWith(new Handler2())
//                .handleEventsWith(new Handler3());
        /**
         * 2.2 并行操作 ：可以分两种方式添加
         * 1、handleEventWith 方法，添加多个handler实现即可
         * 2、handleEventWith方法，分开调用
         */
       // disruptor.handleEventsWith(new Handler1(),new Handler2(),new Handler3());
//        disruptor.handleEventsWith(new Handler1());
//        disruptor.handleEventsWith(new Handler2());
//        disruptor.handleEventsWith(new Handler3());

        /**
         * 2.3 菱形操作
         */
        // 2.3.1 菱形操作一
//        disruptor.handleEventsWith(new Handler1(),new Handler2())
//                .handleEventsWith(new Handler3());
        // 2.3.2 菱形操作二
//        EventHandlerGroup<TradeEvent> eventHandlerGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
//        eventHandlerGroup.then(new Handler3());

        // 2.4 多边形
        Handler1 h1 = new Handler1();
        Handler2 h2 = new Handler2();
        Handler3 h3 = new Handler3();
        Handler4 h4 = new Handler4();
        Handler5 h5 = new Handler5();
        disruptor.handleEventsWith(h1,h4);
        disruptor.after(h1).handleEventsWith(h2);
        disruptor.after(h4).handleEventsWith(h5);
        disruptor.after(h2,h5).handleEventsWith(h3);
        // 3、启动disruptor
        disruptor.start();
        long startTime = System.currentTimeMillis();

        TradeEventProducer producer = new TradeEventProducer(disruptor.getRingBuffer());
        producer.sendData();
        disruptor.shutdown();
        es2.shutdown();
        long endTime = System.currentTimeMillis();
        System.err.println("总耗时： " +(endTime - startTime));

    }
}
