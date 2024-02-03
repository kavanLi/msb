package com.msb.webflux.service;


import com.msb.dao.StockDao;
import com.msb.webflux.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class StockServiceImpl implements StockService{
    @Autowired
    private StockDao stockDao;
    @Override
    public Flux<Stock> getAllStocks() {
        return doGetAllStocks();
    }

    private Flux<Stock> doGetAllStocks() {
        log.info("获取所有的股票信息");
        return stockDao.findAll()
                .map(stockDO -> Stock.builder()
                .symbol(stockDO.getSymbol())
                .name(stockDO.getName())
                .build());
    }
}
