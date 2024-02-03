package com.msb.disruptor.heigh.chain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Event
 */
@Data
public class TradeEvent {
    private String id;
    private String name;
    private double price;
    private AtomicInteger count = new AtomicInteger(0);

}
