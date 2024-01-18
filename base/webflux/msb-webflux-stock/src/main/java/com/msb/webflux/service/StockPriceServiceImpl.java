package com.msb.webflux.service;

import com.msb.dao.StockSubscriptionDao;
import com.msb.entry.StockSubscriptionDO;
import com.msb.webflux.dto.StockPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Slf4j
@Service
public class StockPriceServiceImpl implements StockPriceService{
    @Autowired
    private StockSubscriptionDao stockSubscriptionDao;
    @Autowired
    private PriceQueryEngine priceQueryEngine;

    @Override
    public Flux<StockPrice> getPrice(String email) {
        return doGetPrice(email);
    }

    public Flux<StockPrice> doGetPrice(String email){
        log.info("获取股票价格信息:{}",email);
        Flux<StockSubscriptionDO> subscriptions = stockSubscriptionDao.findByEmail(email);
        return subscriptions
                .map(stockSubscriptionDO -> stockSubscriptionDO.getSymbol())
                .map(symbol -> new StockPrice(symbol,priceQueryEngine.getPriceForSymbol(symbol)));
    }
}













