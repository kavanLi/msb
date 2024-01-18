package com.msb.webflux.service;

import com.msb.webflux.dto.StockPrice;
import reactor.core.publisher.Flux;

public interface StockPriceService {
    Flux<StockPrice> getPrice(String testUserEmail);
}
