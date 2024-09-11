package com.msb.es.controller;

import com.msb.es.dto.ResultDto;
import com.msb.es.util.ESClient;
import lombok.SneakyThrows;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;


@RestController
@RequestMapping("/car")
public class ClientController {

    RestHighLevelClient client = ESClient.getInstance().getHighLevelClient();

    //region index
    @RequestMapping("/init")
    @SneakyThrows
    public String indexInit() {
        CreateIndexRequest request = new CreateIndexRequest("test_index2");
        request.mapping("_doc",
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"message\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );

        //region 另一种方式
        /*XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("message");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(builder);*/
        //endregion
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        if (createIndexResponse.isAcknowledged()) {
            System.out.println("创建index成功!");
        } else {
            System.out.println("创建index失败!");
        }
        return createIndexResponse.index();
    }
    //endregion

    //region 分页查询
    @RequestMapping("/carInfo")
    @SneakyThrows
    public ResultDto carInfo(@RequestParam(value = "keyword", required = true) String keyword,
                             @RequestParam(value = "from", required = true) Integer from,
                             @RequestParam(value = "size", required = true) Integer size) {

        SearchRequest searchRequest = new SearchRequest("msb_auto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("series_name", keyword));
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        ESClient.getInstance().closeClient();
        ResultDto res = new ResultDto();
        res.setData(searchResponse.getHits().getHits());
        return res;
    }
    //endregion

    //region 滚动查询
    @RequestMapping("/scroll")
    @SneakyThrows
    public ResultDto scroll(String scrollId) {
        SearchRequest searchRequest = new SearchRequest("msb_auto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(2);
        searchRequest.source(searchSourceBuilder);

        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
//        SearchResponse searchResponse = null;
//        if (scrollId == null || "".equals(scrollId)) {
//            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        } else {
//            searchResponse = client.scroll(new SearchScrollRequest(scrollId), RequestOptions.DEFAULT);
//        }
        SearchResponse searchResponse = scrollId == null
                ? client.search(searchRequest, RequestOptions.DEFAULT)
                : client.scroll(new SearchScrollRequest(scrollId), RequestOptions.DEFAULT);
        scrollId = searchResponse.getScrollId();
        SearchHits hits = searchResponse.getHits();
//        ESClient.getInstance().closeClient();
        ResultDto res = new ResultDto();
        res.setTag(scrollId);
        res.setData(hits.getHits());
        return res;
    }
    //endregion

    //region fuzzy
    @RequestMapping("/fuzzy")
    @SneakyThrows
    public SearchHit[] fuzzy(String name) {
        SearchRequest searchRequest = new SearchRequest("msb_auto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("brand_name.keyword", name).fuzziness(Fuzziness.AUTO));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return response.getHits().getHits();
    }
    //endregion

    //region Bulk
    @RequestMapping("/bulk")
    @SneakyThrows
    public ResultDto bulk() {

        SearchRequest searchRequest = new SearchRequest("msb_auto");
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest("msb_auto", "13"));
        request.add(new UpdateRequest("msb_auto", "22")
                .doc(XContentType.JSON, "series_name", "天籁之音"));
        request.add(new IndexRequest("msb_auto").id("4")
                .source(XContentType.JSON, "brand_name", "天津一汽"));
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        return null;
    }
    //endregion

    //region Search template
    @RequestMapping("/templateSearch")
    @SneakyThrows
    public ResultDto templateSearch() {

        //region 创建模板并缓存 作用域为整个集群
        Request scriptRequest = new Request("POST", "_scripts/test_template_search");
        scriptRequest.setJsonEntity(
                "{" +
                        "  \"script\": {" +
                        "    \"lang\": \"mustache\"," +
                        "    \"source\": {" +
                        "      \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                        "      \"size\" : \"{{size}}\"" +
                        "    }" +
                        "  }" +
                        "}");
        Response scriptResponse = client.getLowLevelClient().performRequest(scriptRequest);
        //endregion
        //***********************************     华丽的分割线     *******************************************************
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest("msb_auto"));
        request.setScriptType(ScriptType.STORED);
        request.setScript("test_template_search");
        //region 本地模板
        //        request.setScriptType(ScriptType.INLINE);
//        request.setScript(
//                        "{\n" +
//                        "  \"from\": {{from}},\n" +
//                        "  \"size\": {{size}},\n" +
//                        "  \"query\": {\n" +
//                        "    \"match\": {\n" +
//                        "      \"master_brand_name\": \"{{master_brand_name}}\"\n" +
//                        "    }\n" +
//                        "  }\n" +
//                        "}");
        //endregion
        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("field", "master_brand_name");
        scriptParams.put("value", "一汽");
        scriptParams.put("size", 5);
        request.setScriptParams(scriptParams);
        SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
        return null;
    }
    //endregion

    //region Multi-Search
    @RequestMapping("/multiSearch")
    @SneakyThrows
    public ResultDto multiSearch() {
        MultiSearchRequest request = new MultiSearchRequest();

        SearchRequest firstSearchRequest = new SearchRequest("msb_auto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("series_name", "朗动"));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);

        SearchRequest secondSearchRequest = new SearchRequest("msb_auto");
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("series_name", "揽胜运动"));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);

        MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
        return null;
    }
    //endregion

    //region Bool Request
    @RequestMapping("/boolSearch")
    @SneakyThrows
    public ResultDto boolSearch() {
        MultiSearchRequest request = new MultiSearchRequest();

        SearchRequest searchRequest = new SearchRequest("msb_auto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query
                (
                        QueryBuilders.boolQuery()
                                .must(matchPhraseQuery("sale_name", "2018款"))
                                .filter(matchQuery("master_brand_name", "大众").analyzer("ik_max_word"))
                                .mustNot(matchQuery("series_name", "速腾"))
                );
        searchRequest.source(searchSourceBuilder);
        request.add(searchRequest);
        MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
        return null;
    }
    //endregion

}
