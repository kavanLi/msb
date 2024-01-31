package com.msb.disruptor.quickstart;

import com.lmax.disruptor.EventFactory;

public class OrderEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        // 返回一个空事件对象（Event）
        return new OrderEvent();
    }
}
