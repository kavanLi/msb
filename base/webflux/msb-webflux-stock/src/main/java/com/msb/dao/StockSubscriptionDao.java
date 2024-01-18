package com.msb.dao;

import com.msb.entry.StockDO;
import com.msb.entry.StockSubscriptionDO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StockSubscriptionDao extends ReactiveCrudRepository<StockSubscriptionDO,String> {
    Flux<StockSubscriptionDO> findByEmail(String email);
}
