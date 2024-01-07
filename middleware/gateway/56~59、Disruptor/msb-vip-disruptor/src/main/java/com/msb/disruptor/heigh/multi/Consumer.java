package com.msb.disruptor.heigh.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements WorkHandler<Order> {
    private String consumerId;

    private static AtomicInteger count = new AtomicInteger(0);
    private Random random = new Random();

    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order event) throws Exception {
        System.err.println("当前消费者：" +consumerId +  ",消费者消息ID:" + event.getId());
        count.incrementAndGet();
    }
    public int getCount(){
        return count.get();
    }
}
