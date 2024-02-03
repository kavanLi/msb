package com.msb.webflux.service;

import com.msb.entry.StockSubscriptionDO;
import com.msb.webflux.model.StockSubsciption;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscriptionsService {
    Flux<StockSubsciption> findByEmail(String testUserEmail);

    Mono<StockSubscriptionDO> addSubscription(String email, String symbol);
}
