package com.msb.disruptor.quickstart;

import com.lmax.disruptor.RingBuffer;

public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(long orderId){
        // 1 在生产者发送消息时候，首先 需要从我们的ringBuffer里面获取一个可用的序号
        long sequence = ringBuffer.next();
        try{
            // 2、 根据这个序号，这到具体的”OrderEvent“元素  注意：此时获取到的OrderEvent对象是一个没有被赋值的“空对象”
            OrderEvent orderEvent = ringBuffer.get(sequence);
            // 3、 进行实际的赋值处理
            orderEvent.setOrderId(orderId);
        }finally {
            // 4、提交发布操作
            // 这样保证事件一定发布 ，如果我们使用RingBuffe.next获取一个时间槽，那么就一定要发布对应的时间
            // 如果不能发布事件，那么会引起Distuptor状态混乱，尤其是在多个事件生产者的情况下会导致消费者失速，从而不得不重启应用来恢复
            ringBuffer.publish(sequence);
        }


    }
}
