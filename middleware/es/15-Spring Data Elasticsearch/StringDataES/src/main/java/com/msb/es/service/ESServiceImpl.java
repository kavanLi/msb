package com.msb.es.service;

import com.msb.es.entity.MsbCarInfo;
import com.msb.es.util.ESUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class ESServiceImpl<T> {
    @Resource
    private ESUtil esUtil;

    public boolean removeIndex(String indexName){
        return esUtil.delIndex(indexName);
    }

    public boolean addData(List<T> list) {
        if(list.size()<0){
            return true;
        }
        try {
            if(esUtil.createIndex(list.get(0).getClass()))
                return esUtil.batchSaveOrUpdate(list,true);
            else
                return false;
        } catch (Exception e) {
            log.error("############### 数据添加失败! {}", e);
        }
        return false;
    }

    public List<MsbCarInfo> search(SearchSourceBuilder searchSourceBuilder) {
        try {
            return esUtil.search(searchSourceBuilder, MsbCarInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
