package com.msb.disruptor.heigh.chain;


import com.lmax.disruptor.EventHandler;

import java.util.UUID;

public class Handler2 implements EventHandler<TradeEvent> {

    @Override
    public void onEvent(TradeEvent tradeEvent, long l, boolean b) throws Exception {
        System.err.println("handler 2 :set ID");
       // Thread.sleep(2000);
        tradeEvent.setId(UUID.randomUUID().toString());
    }
}
