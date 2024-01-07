package com.msb.disruptor.heigh.chain;

import com.lmax.disruptor.EventFactory;

public class TradeEventFactory  implements EventFactory<TradeEvent> {
    @Override
    public TradeEvent newInstance() {
        return new TradeEvent();
    }
}
