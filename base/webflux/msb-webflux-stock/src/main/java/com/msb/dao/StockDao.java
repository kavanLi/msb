package com.msb.dao;

import com.msb.entry.StockDO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDao extends ReactiveCrudRepository<StockDO,String> {
}
