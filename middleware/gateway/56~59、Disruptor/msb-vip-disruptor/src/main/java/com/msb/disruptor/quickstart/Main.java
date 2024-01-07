package com.msb.disruptor.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        // 指定ringbuffer大小，必须是2的N次方
        int ringBufferSize = 1024;
        // 最好是自定义线程池
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        /**
         * 1、orderEventFactory  :消息（Event） 工厂对象
         * 2、ringBufferSize :容器长度
         * 3、executor:线程池（建议使用自定义线程池）,
         * 4、ProducerType： 单生产，还是多生产
         * 5、waitStrategy:等待策略  ：当队列数据满了或者没有数据的时候，生产者和消费者的等待策略
         */
        // 1、实例话Disruptor
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(orderEventFactory,
                ringBufferSize,
                executor,
                ProducerType.SINGLE, new BlockingWaitStrategy());
        // 2、添加消费者的监听（构建Disruptor 与消费者的一个关联关系）
        disruptor.handleEventsWith(new OrderEventHandler());
        // 3、启动Disruptor
        disruptor.start();
        // 4、获取时间存储数据的容器，RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        OrderEventProducer producer = new OrderEventProducer(ringBuffer);
        for(long i = 0; i < 100;i ++){
            producer.sendData(i);
        }
        // 调用shudown的时候，会等待handler处理完数据后才执行完毕
        disruptor.shutdown();
        executor.shutdown();

    }
}
