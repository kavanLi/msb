package com.msb.webflux.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PriceQueryEngine {
    public String getPriceForSymbol(String symbol){
        Random random = new Random();
        double value = random.nextDouble() * 300 +100;
        return String.format("%.2f",value);
    }
}
