package com.msb.disruptor.heigh.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
         // 1、RingBuffer
        // 这里和创建Disruptor有点区别，创建Disruptor需要指定线程池，如果是多生产多消费者模式，就不需要线程池了
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI,
                () -> new Order(), 1024, new YieldingWaitStrategy());
        //2、通过ringBuffer创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        // 3、创建多个消费者组
        Consumer[] consumers = new Consumer[10];
        for(int i = 0 ;i < consumers.length;i++){
            consumers[i] = new Consumer("C" + i);
        }
        // 4、构建多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<>(ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(),
                consumers);
        // 5、设置多消费者sequence序号，用于单独统计消费者进度
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        // 6、 启动WorkPool
        workerPool.start(Executors.newFixedThreadPool(10));

        CyclicBarrier latch = new CyclicBarrier(100);
        for(int i = 0 ;i < 100;i++){
            Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    for(int j = 0 ;j < 100;j++){
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }
        System.err.println("======线程创建完毕，开始生产数据==========");
        Thread.sleep(2 *1000);
        System.err.println("任务总的数量：" + consumers[0].getCount());
    }
}
