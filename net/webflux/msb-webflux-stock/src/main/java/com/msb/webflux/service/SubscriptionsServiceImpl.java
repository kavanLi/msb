package com.msb.webflux.service;

import com.msb.dao.StockSubscriptionDao;
import com.msb.entry.StockSubscriptionDO;
import com.msb.webflux.model.StockSubsciption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SubscriptionsServiceImpl implements SubscriptionsService{
    @Autowired
    private StockSubscriptionDao subscriptionDao;
    @Override
    public Flux<StockSubsciption> findByEmail(String email) {
        return doFindByEmail(email);
    }

    @Override
    public Mono<StockSubscriptionDO> addSubscription(String email, String symbol) {
        return doAddSubscprition(email,symbol);
    }

    private Mono<StockSubscriptionDO> doAddSubscprition(String email, String symbol) {
        log.info("增加订阅信息：{}",symbol);
        StockSubscriptionDO subscriptionDO = new StockSubscriptionDO();
        subscriptionDO.setEmail(email);
        subscriptionDO.setSymbol(symbol);
        Mono<StockSubscriptionDO> save = subscriptionDao.save(subscriptionDO);
        return save;
    }

    private Flux<StockSubsciption> doFindByEmail(String email) {
        log.info("通过邮件地址获取订阅股票信息：{}",email);
        return subscriptionDao.findByEmail(email)
                .map(stockSubscriptionDO ->
                        StockSubsciption.builder()
                .symbol(stockSubscriptionDO.getSymbol())
                .email(stockSubscriptionDO.getEmail()).build());
    }
}
