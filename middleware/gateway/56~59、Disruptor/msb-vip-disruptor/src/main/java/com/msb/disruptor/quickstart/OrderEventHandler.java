package com.msb.disruptor.quickstart;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;

/**
 * 监听事件【消费者】
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    /**
     * 处理事件
     * @param orderEvent  消费到本事件的主体
     * @param sequence     消费到本事件对应的sequence
     * @param endOfBatch   表示消费到本次时间是否是这个批次中最后一个
     * @throws Exception
     */
    @Override
    public void onEvent(OrderEvent orderEvent, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消费者 处理订单信息 订单ID:" + orderEvent.getOrderId());
    }
}

