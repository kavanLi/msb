package com.example.demo.service.lucene.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.config.LuceneConfig;
import com.example.demo.service.lucene.LuceneService;
import com.mashibing.internal.common.domain.request.FuzzyQueryReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Joyce Huang
 */
@Slf4j
@Service
public class LuceneServiceImpl implements LuceneService {

    @Autowired
    private Directory itemManagementIndex;

    @Autowired
    private Analyzer analyzer;

    @Autowired
    private IndexWriter itemManagementWriter;

    @Override
    public List<String> fuzzyQuery4itemManagement(FuzzyQueryReq req) {
        // 使用 LinkedHashSet 去重并保持插入顺序
        List <String> results = new ArrayList <>();
        String queryStr = req.getQueryStr();

        //if (StringUtils.isEmpty(req.getApplicationId())) {
        //    req.setApplicationId(loginEmployee.getApplicationId());
        //}

        // 校验请求参数
        if (StringUtils.isEmpty(queryStr)) {
            return results;
        }

        try {
            // 创建查询
            Query q = createCombinedQuery(queryStr, analyzer, LuceneConfig.TAG_1, LuceneConfig.TAG_2);

            // 执行查询
            int hitsPerPage = 15;

            // 索引1
            DirectoryReader reader = DirectoryReader.open(itemManagementIndex);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            // 显示查询结果
            Set <String> uniqueSet = new LinkedHashSet <>();
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                String name = d.get(LuceneConfig.NAME);
                log.info((i + 1) + ". " + name + ":" + d.get(LuceneConfig.DESC) + ":" + d.get(LuceneConfig.TAG_1) + ":" + d.get(LuceneConfig.TAG_2));
                if (StringUtils.isNotEmpty(req.getApplicationId()) &&
                        StringUtils.equalsIgnoreCase(req.getApplicationId(), d.get(LuceneConfig.TAG_1))) {
                    if (StringUtils.isNotEmpty(req.getBizUserId()) &&
                            !StringUtils.equalsIgnoreCase(req.getBizUserId(), d.get(LuceneConfig.TAG_2))) {
                        continue;
                    }
                    uniqueSet.add(name);
                }
            }
            reader.close();

            // 提取前10条数据
            results = new ArrayList <>(uniqueSet);
            if (results.size() > 10) {
                List<String> top10Results = results.size() > 10 ? results.subList(0, 10) : results;
                results = top10Results;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

        return results;
    }

    private Query createCombinedQuery(String queryStr, Analyzer analyzer, String appId, String bizUserId) throws Exception {
        String lowerCase = queryStr.toLowerCase();
        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        // PrefixQuery
        PrefixQuery prefixQuery = new PrefixQuery(new Term(LuceneConfig.NAME, lowerCase));
        Query prefixBoostedQuery = new BoostQuery(prefixQuery, 2.0f); // 设置较高的权重

        // QueryParser
        Query queryParserQuery = new QueryParser(LuceneConfig.NAME, analyzer).parse(lowerCase);
        Query queryParserBoostedQuery = new BoostQuery(queryParserQuery, 1.5f); // 设置中等权重


        // 高优先级
        builder.add(queryParserBoostedQuery, BooleanClause.Occur.SHOULD);
        builder.add(prefixBoostedQuery, BooleanClause.Occur.SHOULD);

        // 低优先级
        builder.add(new QueryParser(LuceneConfig.TAG_1, analyzer).parse(appId), BooleanClause.Occur.SHOULD);
        builder.add(new QueryParser(LuceneConfig.TAG_2, analyzer).parse(bizUserId), BooleanClause.Occur.SHOULD);

        return builder.build();
    }

    /**
     * 动态增加新文档
     */
    public void addDocInRealTime(String name, String description, String tag1, String tag2) throws IOException, ParseException {
        // 如果IndexWriter是关闭的，重新打开
        if (itemManagementWriter == null || !itemManagementWriter.isOpen()) {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            itemManagementWriter = new IndexWriter(itemManagementIndex, config);
        }

        // 添加新文档
        Document doc = new Document();
        doc.add(new TextField(LuceneConfig.NAME, name, Field.Store.YES));
        doc.add(new TextField(LuceneConfig.DESC, description, Field.Store.YES));
        doc.add(new StringField(LuceneConfig.TAG_1, tag1, Field.Store.YES));
        doc.add(new StringField(LuceneConfig.TAG_2, tag2, Field.Store.YES));
        itemManagementWriter.addDocument(doc);
        itemManagementWriter.commit();
    }
}
