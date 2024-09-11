package com.msb.es.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageInfo;
import com.msb.es.dto.Document;
import com.msb.es.dto.EsDataId;
import com.msb.es.dto.enums.FieldType;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Component
public class ESUtil {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    private static int index_number_of_shards = 3;//默认分片数

    private static int index_number_of_replicas = 1;//默认副本数  单节点

    public void setIndexNumber(int index_number_of_shards, int index_number_of_replicas) {
        this.index_number_of_shards = index_number_of_shards;
        this.index_number_of_replicas = index_number_of_replicas;
    }

    public RestHighLevelClient getInstance() {
        return restHighLevelClient;
    }

    //region 创建索引(默认分片数为3和副本数为1)
    /**
     * 创建索引(默认分片数为1和副本数为0)
     *
     * @param clazz 根据实体自动映射es索引
     * @throws IOException
     */
    public boolean createIndex(Class clazz) throws Exception {
        Document declaredAnnotation = (Document) clazz.getDeclaredAnnotation(Document.class);
        if (declaredAnnotation == null) {
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", clazz.getName()));
        }
        String indexName = declaredAnnotation.indexName();
        boolean flag = createRootIndex(indexName, clazz);
        if (flag) {
            return true;
        }
        return false;
    }

    /**
     * 创建索引(默认分片数为5和副本数为1)
     *
     * @param clazz 根据实体自动映射es索引
     * @throws IOException
     */
    public boolean createIndexIfNotExist(Class clazz) throws Exception {
        Document declaredAnnotation = (Document) clazz.getDeclaredAnnotation(Document.class);
        if (declaredAnnotation == null) {
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", clazz.getName()));
        }
        String indexName = declaredAnnotation.indexName();

        boolean indexExists = isIndexExists(indexName);
        if (!indexExists) {
            boolean flag = createRootIndex(indexName, clazz);
            if (flag) {
                return true;
            }
        }
        return false;
    }

    private boolean createRootIndex(String indexName, Class clazz) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                // 设置分片数， 副本数
                .put("index.number_of_shards", index_number_of_shards)
                .put("index.number_of_replicas", index_number_of_replicas)
        );
        request.mapping(generateBuilder(clazz));
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();
        // 指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        return acknowledged || shardsAcknowledged;
    }
    //endregion

    //region 更新索引
    /**
     * 更新索引(默认分片数为5和副本数为1)：
     * 只能给索引上添加一些不存在的字段
     * 已经存在的映射不能改
     *
     * @param clazz 根据实体自动映射es索引
     * @throws IOException
     */
    public boolean updateIndex(Class clazz) throws Exception {
        Document declaredAnnotation = (Document) clazz.getDeclaredAnnotation(Document.class);
        if (declaredAnnotation == null) {
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", clazz.getName()));
        }
        String indexName = declaredAnnotation.indexName();
        PutMappingRequest request = new PutMappingRequest(indexName);

        request.source(generateBuilder(clazz));
        AcknowledgedResponse response = restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();

        if (acknowledged) {
            return true;
        }
        return false;
    }
    //endregion

    //region 删除索引
    /**
     * 删除索引
     *
     * @param indexName
     * @return
     */
    public boolean delIndex(String indexName) {
        boolean acknowledged = false;
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = delete.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }
    //endregion

    //region 判断索引是否存在
    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     */
    public boolean isIndexExists(String indexName) {
        boolean exists = false;
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            getIndexRequest.humanReadable(true);
            exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }
    //endregion

    //region 添加单条数据
    /**
     * 添加单条数据
     * 提供多种方式：
     * 1. json
     * 2. map
     * Map<String, Object> jsonMap = new HashMap<>();
     * jsonMap.put("user", "kimchy");
     * jsonMap.put("postDate", new Date());
     * jsonMap.put("message", "trying out Elasticsearch");
     * IndexRequest indexRequest = new IndexRequest("posts")
     * .id("1").source(jsonMap);
     * 3. builder
     * XContentBuilder builder = XContentFactory.jsonBuilder();
     * builder.startObject();
     * {
     * builder.field("user", "kimchy");
     * builder.timeField("postDate", new Date());
     * builder.field("message", "trying out Elasticsearch");
     * }
     * builder.endObject();
     * IndexRequest indexRequest = new IndexRequest("posts")
     * .id("1").source(builder);
     * 4. source:
     * IndexRequest indexRequest = new IndexRequest("posts")
     * .id("1")
     * .source("user", "kimchy",
     * "postDate", new Date(),
     * "message", "trying out Elasticsearch");
     * <p>
     * 报错：  Validation Failed: 1: type is missing;
     * 加入两个jar包解决
     * <p>
     * 提供新增或修改的功能
     *
     * @return
     */
    public IndexResponse index(Object o) throws Exception {
        Document declaredAnnotation = (Document) o.getClass().getDeclaredAnnotation(Document.class);
        if (declaredAnnotation == null) {
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", o.getClass().getName()));
        }
        String indexName = declaredAnnotation.indexName();

        IndexRequest request = new IndexRequest(indexName);
        Field fieldByAnnotation = getFieldByAnnotation(o, EsDataId.class);
        if (fieldByAnnotation != null) {
            fieldByAnnotation.setAccessible(true);
            try {
                Object id = fieldByAnnotation.get(o);
                request = request.id(id.toString());
            } catch (IllegalAccessException e) {
            }
        }

        String userJson = JSON.toJSONString(o);
        request.source(userJson, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return indexResponse;
    }
    //endregion

    //region queryById
    /**
     * 根据id查询
     *
     * @return
     */
    public String queryById(String indexName, String id) throws IOException {
        GetRequest getRequest = new GetRequest(indexName, id);
        // getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);

        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        String jsonStr = getResponse.getSourceAsString();
        return jsonStr;
    }
    //endregion

    //region 查询封装返回json字符串
    /**
     * 查询封装返回json字符串
     *
     * @param indexName
     * @param searchSourceBuilder
     * @return
     * @throws IOException
     */
    public String search(String indexName, SearchSourceBuilder searchSourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHits hits = searchResponse.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSON.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }
    //endregion

    //region 查询封装，带分页
    /**
     * 查询封装，带分页
     *
     * @param searchSourceBuilder
     * @param pageNum
     * @param pageSize
     * @param s
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> PageInfo<T> search(SearchSourceBuilder searchSourceBuilder, int pageNum, int pageSize, Class<T> s) throws Exception {
        Document declaredAnnotation = (Document) s.getDeclaredAnnotation(Document.class);
        if (declaredAnnotation == null) {
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", s.getName()));
        }
        String indexName = declaredAnnotation.indexName();
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSON.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        int total = (int) hits.getTotalHits().value;

        // 封装分页
        List<T> list = jsonArray.toJavaList(s);
        PageInfo<T> page = new PageInfo<>();
        page.setList(list);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(total);
        page.setPages(total == 0 ? 0 : (total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1));
        page.setHasNextPage(page.getPageNum() < page.getPages());
        return page;
    }
    //endregion

    //region 查询封装，返回集合
    /**
     * 查询封装，返回集合
     *
     * @param searchSourceBuilder
     * @param s
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> List<T> search(SearchSourceBuilder searchSourceBuilder, Class<T> s) throws Exception {
        Document declaredAnnotation = s.getDeclaredAnnotation(Document.class);
        if (declaredAnnotation == null) {
            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", s.getName()));
        }
        String indexName = declaredAnnotation.indexName();
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // //配置标题高亮显示
        // HighlightBuilder highlightBuilder = new HighlightBuilder(); //生成高亮查询器
        // highlightBuilder.field(title);      //高亮查询字段
        // highlightBuilder.field(content);    //高亮查询字段
        // highlightBuilder.requireFieldMatch(false);     //如果要多个字段高亮,这项要为false
        // highlightBuilder.preTags("<span style=\"color:red\">");   //高亮设置
        // highlightBuilder.postTags("</span>");
        //
        // //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
        // highlightBuilder.fragmentSize(800000); //最大高亮分片数
        // highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段

        String scrollId = searchResponse.getScrollId();
        SearchHits hits = searchResponse.getHits();
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSON.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        // 封装分页
        List<T> list = jsonArray.toJavaList(s);
        return list;
    }
    //endregion

    //region 批量插入文档
    /**
     * 批量插入文档
     * 文档存在 则插入
     * 文档不存在 则更新
     *
     * @param list
     * @return
     */
    public <T> boolean batchSaveOrUpdate(List<T> list, boolean izAsync) throws Exception {
        Object o1 = list.get(0);
        Document declaredAnnotation = (Document) o1.getClass().getDeclaredAnnotation(Document.class);
        if (declaredAnnotation == null) {
            throw new Exception(String.format("class name: %s can not find Annotation [@Document], please check", o1.getClass().getName()));
        }
        String indexName = declaredAnnotation.indexName();

        BulkRequest request = new BulkRequest(indexName);
        for (Object o : list) {
            String jsonStr = JSON.toJSONString(o);
            IndexRequest indexReq = new IndexRequest().source(jsonStr, XContentType.JSON);

            Field fieldByAnnotation = getFieldByAnnotation(o, EsDataId.class);
            if (fieldByAnnotation != null) {
                fieldByAnnotation.setAccessible(true);
                try {
                    Object id = fieldByAnnotation.get(o);
                    indexReq = indexReq.id(id.toString());
                } catch (IllegalAccessException e) {
                }
            }
            request.add(indexReq);
        }
        if (izAsync) {
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            return outResult(bulkResponse);
        } else {
            restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkResponse) {
                    outResult(bulkResponse);
                }

                @Override
                public void onFailure(Exception e) {
                }
            });
        }
        return true;
    }
    //endregion

    //region 删除文档
    /**
     * 删除文档
     *
     * @param indexName： 索引名称
     * @param docId：     文档id
     */
    public boolean deleteDoc(String indexName, String docId) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName, docId);
        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        // 解析response
        String index = deleteResponse.getIndex();
        String id = deleteResponse.getId();
        long version = deleteResponse.getVersion();
        ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
            }
        }
        return true;
    }
    //endregion

    //region 根据json类型更新文档
    /**
     * 根据json类型更新文档
     *
     * @param indexName
     * @param docId
     * @param o
     * @return
     * @throws IOException
     */
    public boolean updateDoc(String indexName, String docId, Object o) throws IOException {
        UpdateRequest request = new UpdateRequest(indexName, docId);
        request.doc(JSON.toJSONString(o), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        String index = updateResponse.getIndex();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
        } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
        }
        return false;
    }
    //endregion

    //region 根据Map类型更新文档
    /**
     * 根据Map类型更新文档
     *
     * @param indexName
     * @param docId
     * @param map
     * @return
     * @throws IOException
     */
    public boolean updateDoc(String indexName, String docId, Map<String, Object> map) throws IOException {
        UpdateRequest request = new UpdateRequest(indexName, docId);
        request.doc(map);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        String index = updateResponse.getIndex();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
        } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {

        }
        return false;
    }
    //endregion

    //region generateBuilder
    public XContentBuilder generateBuilder(Class clazz) throws IOException {
        // 获取索引名称及类型
        Document doc = (Document) clazz.getAnnotation(Document.class);
        System.out.println(doc.indexName());

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.startObject("properties");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            if (f.isAnnotationPresent(com.msb.es.dto.Field.class)) {
                // 获取注解
                com.msb.es.dto.Field declaredAnnotation =
                        f.getDeclaredAnnotation(com.msb.es.dto.Field.class);

                // 如果嵌套对象：
                /**
                 * {
                 *   "mappings": {
                 *     "properties": {
                 *       "region": {
                 *         "type": "keyword"
                 *       },
                 *       "manager": {
                 *         "properties": {
                 *           "age":  { "type": "integer" },
                 *           "name": {
                 *             "properties": {
                 *               "first": { "type": "text" },
                 *               "last":  { "type": "text" }
                 *             }
                 *           }
                 *         }
                 *       }
                 *     }
                 *   }
                 * }
                 */
                if (declaredAnnotation.type() == FieldType.OBJECT) {
                    // 获取当前类的对象-- Action
                    Class<?> type = f.getType();
                    Field[] df2 = type.getDeclaredFields();
                    builder.startObject(f.getName());
                    builder.startObject("properties");
                    // 遍历该对象中的所有属性
                    for (Field f2 : df2) {
                        if (f2.isAnnotationPresent(com.msb.es.dto.Field.class)) {
                            // 获取注解
                            com.msb.es.dto.Field declaredAnnotation2 = f2.getDeclaredAnnotation(com.msb.es.dto.Field.class);
                            builder.startObject(f2.getName());
                            builder.field("type", declaredAnnotation2.type().getType());
                            // keyword不需要分词
                            if (declaredAnnotation2.type() == FieldType.TEXT) {
                                builder.field("analyzer", declaredAnnotation2.analyzer().getType());
                            }
                            if (declaredAnnotation2.type() == FieldType.DATE) {
                                builder.field("format", "yyyy-MM-dd HH:mm:ss");
                            }
                            builder.endObject();
                        }
                    }
                    builder.endObject();
                    builder.endObject();

                } else {
                    builder.startObject(f.getName());
                    builder.field("type", declaredAnnotation.type().getType());
                    // keyword不需要分词
                    if (declaredAnnotation.type() == FieldType.TEXT) {
                        builder.field("analyzer", declaredAnnotation.analyzer().getType());
                    }
                    if (declaredAnnotation.type() == FieldType.DATE) {
                        builder.field("format", "yyyy-MM-dd HH:mm:ss");
                    }
                    builder.endObject();
                }
            }
        }
        // 对应property
        builder.endObject();
        builder.endObject();
        return builder;
    }
    //endregion

    //region getFieldByAnnotation
    public static Field getFieldByAnnotation(Object o, Class annotationClass) {
        Field[] declaredFields = o.getClass().getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field f : declaredFields) {
                if (f.isAnnotationPresent(annotationClass)) {
                    return f;
                }
            }
        }
        return null;
    }
    //endregion

    //region getLowLevelClient
    /**
     * getLowLevelClient
     *
     * @return
     */
    public RestClient getLowLevelClient() {
        return restHighLevelClient.getLowLevelClient();
    }
    //endregion

    //region 高亮结果集 特殊处理
    /**
     * 高亮结果集 特殊处理
     * map转对象 JSONObject.parseObject(JSONObject.toJSONString(map), Content.class)
     *
     * @param searchResponse
     * @param highlightField
     */
    public List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, HighlightField> high = hit.getHighlightFields();
            HighlightField title = high.get(highlightField);

            hit.getSourceAsMap().put("id", hit.getId());

            Map<String, Object> sourceAsMap = hit.getSourceAsMap();//原来的结果
            //解析高亮字段,将原来的字段换为高亮字段
            if (title != null) {
                Text[] texts = title.fragments();
                String nTitle = "";
                for (Text text : texts) {
                    nTitle += text;
                }
                //替换
                sourceAsMap.put(highlightField, nTitle);
            }
            list.add(sourceAsMap);
        }
        return list;
    }
    //endregion

    //region 查询并分页
    /**
     * 查询并分页
     *
     * @param index          索引名称
     * @param query          查询条件
     * @param size           文档大小限制
     * @param from           从第几页开始
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public List<Map<String, Object>> searchListData(String index,
                                                    SearchSourceBuilder query,
                                                    Integer size,
                                                    Integer from,
                                                    String fields,
                                                    String sortField,
                                                    String highlightField) throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder builder = query;
        if (StringUtils.isNotEmpty(fields)) {
            //只查询特定字段。如果需要查询所有字段则不设置该项。
            builder.fetchSource(new FetchSourceContext(true, fields.split(","), Strings.EMPTY_ARRAY));
        }
        from = from <= 0 ? 0 : from * size;
        //设置确定结果要从哪个索引开始搜索的from选项，默认为0
        builder.from(from);
        builder.size(size);
        if (StringUtils.isNotEmpty(sortField)) {
            //排序字段，注意如果proposal_no是text类型会默认带有keyword性质，需要拼接.keyword
            builder.sort(sortField + ".keyword", SortOrder.ASC);
        }
        //高亮
        HighlightBuilder highlight = new HighlightBuilder();
        highlight.field(highlightField);
        //关闭多个高亮
        highlight.requireFieldMatch(false);
        highlight.preTags("<span style='color:red'>");
        highlight.postTags("</span>");
        builder.highlighter(highlight);
        //不返回源数据。只有条数之类的数据。
        //builder.fetchSource(false);
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        if (response.status().getStatus() == 200) {
            // 解析对象
            return setSearchResponse(response, highlightField);
        }
        return null;
    }
    //endregion

    private boolean outResult(BulkResponse bulkResponse) {
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            IndexResponse indexResponse = (IndexResponse) itemResponse;
            if (bulkItemResponse.isFailed()) {
                return false;
            }
        }
        return true;
    }
}
