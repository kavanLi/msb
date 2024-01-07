package com.msb.disruptor.performance;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorTest {
    public static void main(String[] args) {
        // TODO 自定义线程成
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int ringBufferSize = 65536;
        Disruptor<Data> disruptor = new Disruptor<Data>(new EventFactory<Data>() {
            @Override
            public Data newInstance() {
                return new Data();
            }
        },
                ringBufferSize,
                executor ,
                ProducerType.SINGLE,
                new YieldingWaitStrategy());
        DataConsumer consumer = new DataConsumer();
        // 消费数据
        disruptor.handleEventsWith(consumer);
        disruptor.start();
        new Thread(() -> {
            RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();
            for(long i = 0; i < Constants.EVENT_NUM; i ++){
                long seq = ringBuffer.next();
                Data data = ringBuffer.get(seq);
                data.setId(i);
                data.setName("name:" + i);
                ringBuffer.publish(seq);
            }
        }).start();



    }
}
