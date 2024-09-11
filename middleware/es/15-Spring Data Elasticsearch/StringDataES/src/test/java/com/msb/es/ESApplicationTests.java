package com.msb.es;

import com.msb.es.entity.MsbCarInfo;
import com.msb.es.service.ESServiceImpl;
import com.msb.es.util.ESUtil;
import lombok.SneakyThrows;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.elasticsearch.action.index.IndexRequest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonMap;
import static org.elasticsearch.action.support.WriteRequest.RefreshPolicy.IMMEDIATE;

@SpringBootTest
class ESApplicationTests {
    @Resource
    RestHighLevelClient highLevelClient;
    @Resource
    private ESServiceImpl<MsbCarInfo> searchServiceImp;
    @Resource
    private ESUtil esUtil;
    @SneakyThrows
    @Test
    public void testCreate() {
        IndexRequest request = new IndexRequest("spring-data")
                .id("1")
                .source(singletonMap("feature", "high-level-rest-client"))
                .setRefreshPolicy(IMMEDIATE);

        IndexResponse response = highLevelClient.index(request,RequestOptions.DEFAULT);
        System.out.println("s");
    }

    @Test
    public void createIndex() throws Exception {
        boolean res = esUtil.createIndex(MsbCarInfo.class);
        System.out.println(res);
    }

    @Test
    public void delIndex() {
        boolean res = esUtil.delIndex("msb_car_info");
        System.out.println(res);
    }

    @Test
    public void queryDataById() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        // 符合条件查询
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.termQuery("name.keyword","大众Fox"));
        searchSourceBuilder.query(boolBuilder);
        List<MsbCarInfo> list = esUtil.search(searchSourceBuilder, MsbCarInfo.class);
        System.out.println(list);
    }

    @Test
    public void batchSaveOrUpdate() throws Exception {
        MsbCarInfo msbCarInfo = new MsbCarInfo();
        msbCarInfo.setId(1);
        msbCarInfo.setName("马士兵教育");
        msbCarInfo.setStatus(1);
        List<MsbCarInfo> list = new ArrayList<>();
        list.add(msbCarInfo);
        esUtil.batchSaveOrUpdate(list,true);
        System.out.println("s");
    };
}
































