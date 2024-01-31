package com.msb.disruptor.heigh.chain;


import com.lmax.disruptor.EventHandler;

public class Handler1 implements EventHandler<TradeEvent> {

    @Override
    public void onEvent(TradeEvent tradeEvent, long l, boolean b) throws Exception {
        System.err.println("handler 1: set name");
      //  Thread.sleep(1000);
        tradeEvent.setName("H1");
    }
}
