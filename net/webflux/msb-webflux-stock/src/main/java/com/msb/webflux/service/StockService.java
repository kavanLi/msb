package com.msb.webflux.service;

import com.msb.webflux.model.Stock;
import reactor.core.publisher.Flux;

public interface StockService {
    Flux<Stock> getAllStocks();
}
