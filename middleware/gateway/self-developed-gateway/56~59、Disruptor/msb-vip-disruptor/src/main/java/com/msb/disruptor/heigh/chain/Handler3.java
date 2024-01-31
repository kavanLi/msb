package com.msb.disruptor.heigh.chain;

import com.lmax.disruptor.EventHandler;

public class Handler3 implements EventHandler<TradeEvent> {

	public void onEvent(TradeEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.err.println("handler 3 : NAME: " 
								+ event.getName() 
								+ ", ID: " 
								+ event.getId()
								+ ", PRICE: " 
								+ event.getPrice()
								+ " INSTANCE : " + event.toString());
	}

}
