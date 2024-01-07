package com.msb.disruptor.heigh.multi;

import com.lmax.disruptor.ExceptionHandler;

public class EventExceptionHandler implements ExceptionHandler<Order> {
    /**
     * 在消费的时候出现异常
     * @param ex
     * @param sequence
     * @param event
     */
    @Override
    public void handleEventException(Throwable ex, long sequence, Order event) {
        ex.printStackTrace();
    }
    // 当启动出现异常
    @Override
    public void handleOnStartException(Throwable ex) {
        ex.printStackTrace();
    }
    // 当消费结束的时候
    @Override
    public void handleOnShutdownException(Throwable ex) {
        ex.printStackTrace();
    }
}
