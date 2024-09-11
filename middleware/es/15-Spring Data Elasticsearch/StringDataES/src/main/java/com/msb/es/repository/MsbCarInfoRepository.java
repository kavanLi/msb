package com.msb.es.repository;

import com.msb.es.entity.MsbCarInfo;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MsbCarInfoRepository extends Repository<MsbCarInfo, String> {
    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    List<MsbCarInfo> findByName(String name);
}
