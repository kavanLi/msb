package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.demo.config.LuceneConfig;
import com.example.demo.service.lucene.LuceneService;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.mashibing.internal.common.annotation.RateLimit;
import com.mashibing.internal.common.domain.request.FuzzyQueryReq;
import com.mashibing.internal.common.domain.response.ServerResp;
import com.mashibing.internal.common.domain.response.ServerRespEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.function.FunctionScoreQuery;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模糊查询
 *
 * @author: kavanLi
 * @create: 2020-01-06 14:07
 */
@RestController
@Slf4j
@RequestMapping("/lucene")
public class FuzzyQueryController {



    @Autowired
    private LuceneService luceneService;

    @PostMapping(value = "/lucene/fuzzyQuery/itemManagement")
    @RateLimit(permitsPerSecond = 10, interval = 5) // 限流  每10秒限制10个请求
    public ServerResp <?> fuzzyQuery4itemManagement(FuzzyQueryReq req) {
        //选定当前登录用户的平台
        //LoginEmployeeDTO loginEmployee = LoginEmployeeThreadLocal.get();
        return ServerRespEntity.success(luceneService.fuzzyQuery4itemManagement(req));
    }


    @Autowired
    private Directory itemManagementIndex;

    @Autowired
    private Directory tempIndex;

    @Autowired
    private Analyzer analyzer;

    @Autowired
    private IndexWriter itemManagementWriter;

    public void addDocInRealTime(String name, String description, String tag1, String tag2) throws IOException, ParseException {
        // 如果IndexWriter是关闭的，重新打开
        if (itemManagementWriter == null || !itemManagementWriter.isOpen()) {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            itemManagementWriter = new IndexWriter(itemManagementIndex, config);
        }

        // 删除旧文档 建议用deleteAll()来全删然后重新加载
        // 构造查询
        //Query query = new QueryParser(LuceneConfig.NAME, analyzer).parse(name);
        ////String[] fields = { LuceneConfig.NAME, LuceneConfig.TAG_1, LuceneConfig.TAG_2 };
        ////String[] values = { name, tag1, tag2 };
        ////QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        ////Query query = parser.parse(String.join(" ", values));
        //// 执行查询
        //DirectoryReader reader = DirectoryReader.open(itemManagementIndex);
        //IndexSearcher searcher = new IndexSearcher(reader);
        //TopDocs topDocs = searcher.search(query, 1024);
        //ScoreDoc[] hits = topDocs.scoreDocs;
        //
        //for (int i = 0; i < hits.length; ++i) {
        //    int docId = hits[i].doc;
        //    Document doc = searcher.doc(docId);
        //    System.out.println("Doc ID: " + i + ", Name: " + doc.get(LuceneConfig.NAME)
        //            + ", Tag1: " + doc.get(LuceneConfig.TAG_1)
        //            + ", Tag2: " + doc.get(LuceneConfig.TAG_2));
        //    if (doc.get(LuceneConfig.NAME).equals(name) &&
        //            doc.get(LuceneConfig.TAG_1).equals(tag1) &&
        //            doc.get(LuceneConfig.TAG_2).equals(tag2)) {
        //        itemManagementWriter.tryDeleteDocument(reader, docId);
        //        itemManagementWriter.commit();
        //    }
        //}

        // 打印所有文档内容
        //for (int i = 0; i < reader.maxDoc(); i++) {
        //    Document doc = searcher.doc(i);
        //    System.out.println("Doc ID: " + i + ", Name: " + doc.get(LuceneConfig.NAME)
        //            + ", Tag1: " + doc.get(LuceneConfig.TAG_1)
        //            + ", Tag2: " + doc.get(LuceneConfig.TAG_2));
        //}

        //itemManagementWriter.deleteDocuments(query);
        //itemManagementWriter.commit();
        //// ------------- forceMergeDeletes() 方法用于强制合并索引，将所有已删除的文档从索引中彻底移除。这是通过执行一个合并操作来完成的，该操作会创建一个新的段，不包括已删除的文档，然后替换旧的段。这个方法会使得删除操作真正生效，从而释放被已删除文档占用的磁盘空间。 -------------
        //itemManagementWriter.forceMergeDeletes();

        // 添加新文档
        Document doc = new Document();
        doc.add(new TextField(LuceneConfig.NAME, name, Field.Store.YES));
        doc.add(new TextField(LuceneConfig.DESC, description, Field.Store.YES));
        doc.add(new StringField(LuceneConfig.TAG_1, tag1, Field.Store.YES));
        doc.add(new StringField(LuceneConfig.TAG_2, tag2, Field.Store.YES));
        itemManagementWriter.addDocument(doc);
        itemManagementWriter.commit();

    }


    @PostMapping(value = "")
    public List <String> test1(String queryStr) {
        List<String> results = new ArrayList<>();
        try {

            // 创建查询
            //JiebaSegmenter segmenter = new JiebaSegmenter();
            //List<SegToken> tokens = segmenter.process(queryStr, JiebaSegmenter.SegMode.SEARCH);
            //StringBuilder sb = new StringBuilder();
            //for (SegToken token : tokens) {
            //    sb.append(token.word).append(" ");
            //}
            //String segmentedQuery = sb.toString().trim();
            //Query q = new PrefixQuery(new Term("name", segmentedQuery.toLowerCase()));
            //Query q = new QueryParser("name", analyzer).parse(segmentedQuery.toLowerCase());
            //Query q = new FuzzyQuery((new Term("name", segmentedQuery.toLowerCase())), BooleanClause.Occur.SHOULD);

            // 动态增加文档
            //addDocInRealTime("机械革命 XG78", "机械革命", "electronics", "phones");



            // 创建查询

            Query q = createCombinedQuery(queryStr, analyzer, "", "");
            //Query q = new PrefixQuery(new Term("name", queryStr.toLowerCase()));
            //Query q = new QueryParser("name", analyzer).parse(queryStr);

            // 执行查询
            int hitsPerPage = 10;

            // 索引1
            DirectoryReader reader = DirectoryReader.open(itemManagementIndex);
            IndexSearcher searcher = new IndexSearcher(reader);

            // ------------- 在进行查询时，可以使用调试工具或日志记录来验证实际索引的内容和查询条件是否一致。你可以查看 Lucene 的索引内容或通过使用 IndexSearcher 的 explain 方法来理解查询的匹配情况： -------------
            //Explanation explanation = searcher.explain(q, 1);
            //System.out.println(explanation.toString());

            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;


            // 打印所有文档内容
            for (int i = 0; i < reader.maxDoc(); i++) {
                Document doc = searcher.doc(i);
                System.out.println("Doc ID: " + i + ", Name: " + doc.get(LuceneConfig.NAME)
                        + ", Tag1: " + doc.get(LuceneConfig.TAG_1)
                        + ", Tag2: " + doc.get(LuceneConfig.TAG_2));
            }

            // 显示查询结果
            System.out.println("Found " + hits.length + " hits.");
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                results.add((i + 1) + ". " + d.get(LuceneConfig.NAME) + "\t" + d.get(LuceneConfig.DESC) + "\t" + d.get(LuceneConfig.TAG_1) +
                        "\t" + d.get(LuceneConfig.TAG_2));
                log.info((i + 1) + ". " + d.get(LuceneConfig.NAME) + "\t" + d.get(LuceneConfig.DESC) + "\t" + d.get(LuceneConfig.TAG_1) + "\t" + d.get(LuceneConfig.TAG_2));
            }
            reader.close();

            // 索引2
            DirectoryReader reader1 = DirectoryReader.open(tempIndex);
            IndexSearcher searcher1 = new IndexSearcher(reader1);
            TopDocs docs1 = searcher1.search(q, hitsPerPage);
            ScoreDoc[] hits1 = docs1.scoreDocs;
            for (int i = 0; i < hits1.length; ++i) {
                int docId = hits1[i].doc;
                Document d = searcher1.doc(docId);
                results.add((i + 1) + ". " + d.get(LuceneConfig.NAME) + "\t" + d.get(LuceneConfig.DESC) + "\t" + d.get(LuceneConfig.TAG_1) +
                        "\t" + d.get(LuceneConfig.TAG_2));
                log.info((i + 1) + ". " + d.get(LuceneConfig.NAME) + "\t" + d.get(LuceneConfig.DESC) + "\t" + d.get(LuceneConfig.TAG_1) + "\t" + d.get(LuceneConfig.TAG_2));
            }
            reader1.close();

            //for (ScoreDoc scoreDoc : docs1.scoreDocs) {
            //    Document doc = searcher1.doc(scoreDoc.doc);
            //    System.out.println("Name: " + doc.get("name"));
            //    System.out.println("Description: " + doc.get("description"));
            //    System.out.println("AppId: " + doc.get("appId"));
            //    System.out.println("BizUserId: " + doc.get("bizUserId"));
            //}
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return results;
    }

    private Query createCombinedQuery(String queryStr, Analyzer analyzer, String appId, String bizUserId) throws Exception {
        // 使用 Jieba 分词
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> tokens = segmenter.process(queryStr, JiebaSegmenter.SegMode.SEARCH);
        StringBuilder sb = new StringBuilder();
        for (SegToken token : tokens) {
            sb.append(token.word).append(" ");
        }
        String segmentedQuery = sb.toString().trim();

        // ------------- 前置条件 -------------
        appId = "electronics";
        bizUserId = "phones";

        String lowerCase = queryStr.toLowerCase();
        //TermQuery termQuery = new TermQuery(new Term(LuceneConfig.TAG_1
        //        , appId.toLowerCase()));
        //TermQuery termQuery1 = new TermQuery(new Term(LuceneConfig.TAG_2, bizUserId.toLowerCase()));

        Query termQuery = new QueryParser(LuceneConfig.TAG_1, analyzer).parse(appId);
        Query termQuery1 = new QueryParser(LuceneConfig.TAG_2, analyzer).parse(bizUserId);
        Query termQuery2 = new QueryParser(LuceneConfig.NAME, analyzer).parse(lowerCase);

        // PrefixQuery
        PrefixQuery prefixQuery = new PrefixQuery(new Term(LuceneConfig.NAME, lowerCase));
        Query prefixBoostedQuery = new BoostQuery(prefixQuery, 2.0f); // 设置较高的权重

        // QueryParser
        Query queryParserQuery = new QueryParser(LuceneConfig.NAME, analyzer).parse(lowerCase);
        Query queryParserBoostedQuery = new BoostQuery(queryParserQuery, 1.5f); // 设置中等权重

        // FuzzyQuery
        //FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("name", queryStr.toLowerCase()));
        //Query fuzzyBoostedQuery = new BoostQuery(fuzzyQuery, 0.5f); // 设置较低的权重

        /**
         * 1.DisjunctionMaxQuery 是一个 BooleanQuery 的变体，允许你组合多个查询，结果是这些查询中得分最高的文档。它在处理模糊查询和带有多种查询条件时特别有用。
         */
        // 将它们组合成 DisjunctionMaxQuery
        //DisjunctionMaxQuery dmQuery = new DisjunctionMaxQuery(Arrays.asList(prefixBoostedQuery,
        //        queryParserBoostedQuery, new BoostQuery(termQuery, 1.0f), new BoostQuery(termQuery1, 1.0f)), 0.0f);
        //return dmQuery;

        /**
         * 2.如果你希望文档只需要满足其中一个条件，就使用 BooleanClause.Occur.SHOULD。
         */
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        //QueryParser level1Parser = new QueryParser("appId", analyzer);
        //Query preset1Query = level1Parser.parse(appId.toLowerCase());
        //builder.add(termQuery, BooleanClause.Occur.MUST);

        //QueryParser level2Parser = new QueryParser("bizUserId", analyzer);
        //Query preset2Query = level2Parser.parse(bizUserId.toLowerCase());

        // 高优先级
        builder.add(prefixBoostedQuery, BooleanClause.Occur.SHOULD);
        builder.add(queryParserBoostedQuery, BooleanClause.Occur.SHOULD);

        // 低优先级
        //builder.add(termQuery1, BooleanClause.Occur.SHOULD);
        //builder.add(termQuery2, BooleanClause.Occur.SHOULD);

        //builder.add(fuzzyBoostedQuery, BooleanClause.Occur.SHOULD);

        /**
         * 3.FunctionScoreQuery 可以让你基于某些查询条件自定义文档的评分。
         */
        //// 构建查询
        //Query query = new BooleanQuery.Builder()
        //        .add(prefixBoostedQuery, BooleanClause.Occur.SHOULD)
        //        .add(queryParserBoostedQuery, BooleanClause.Occur.SHOULD)
        //        .build();
        //
        //// 使用 FunctionScoreQuery 来调整评分
        //FunctionScoreQuery functionScoreQuery = new FunctionScoreQuery(query, new FieldValueFactorQuery(LuceneConfig.NAME));
        //return functionScoreQuery;


        //builder.add(new PrefixQuery(new Term("name", segmentedQuery.toLowerCase())), BooleanClause.Occur.SHOULD);
        //builder.add(new QueryParser("name", analyzer).parse(segmentedQuery.toLowerCase()), BooleanClause.Occur.SHOULD);
        //
        //// FuzzyQuery
        //builder.add(new FuzzyQuery(new Term("name", segmentedQuery)), BooleanClause.Occur.SHOULD);

        // RegexpQuery
        //builder.add(new RegexpQuery(new Term("name", ".*" + segmentedQuery + ".*")), BooleanClause.Occur.SHOULD);

        // TermQuery
        //builder.add(new TermQuery(new Term("name", segmentedQuery)), BooleanClause.Occur.SHOULD);

        // WildcardQuery
        //builder.add(new WildcardQuery(new Term("name", "*" + segmentedQuery + "*")), BooleanClause.Occur.SHOULD);

        // PhraseQuery
        //PhraseQuery.Builder phraseBuilder = new PhraseQuery.Builder();
        //for (SegToken token : tokens) {
        //    phraseBuilder.add(new Term("name", token.word));
        //}
        //builder.add(phraseBuilder.build(), BooleanClause.Occur.SHOULD);
        return builder.build();
    }


    //private void addDoc(IndexWriter writer, String name, String description) throws Exception {
    //    Document doc = new Document();
    //
    //    // Tokenize name and description using Jieba
    //    JiebaSegmenter segmenter = new JiebaSegmenter();
    //    List<SegToken> nameTokens = segmenter.process(name, JiebaSegmenter.SegMode.SEARCH);
    //    List<SegToken> descTokens = segmenter.process(description, JiebaSegmenter.SegMode.SEARCH);
    //
    //    // Add tokens to document
    //    for (SegToken token : nameTokens) {
    //        doc.add(new TextField("name", token.word, Field.Store.YES));
    //    }
    //    for (SegToken token : descTokens) {
    //        doc.add(new TextField("description", token.word, Field.Store.YES));
    //    }
    //
    //    writer.addDocument(doc);
    //}

    public class CustomAnalyzer extends Analyzer {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            StandardTokenizer tokenizer = new StandardTokenizer();
            TokenStream filter = new LowerCaseFilter(tokenizer);
            filter = new WordDelimiterFilter(filter, WordDelimiterFilter.CATENATE_WORDS, null);
            return new TokenStreamComponents(tokenizer, filter);
        }
    }

    //public class HanLPTokenizer extends Tokenizer {
    //    private final Segment segment;
    //    private final List <com.hankcs.hanlp.seg.common.Term> termList;
    //    private final LinkedList <com.hankcs.hanlp.seg.common.Term> termQueue;
    //    private final CharTermAttribute charTermAttr;
    //    private final OffsetAttribute offsetAttr;
    //    private final TypeAttribute typeAttr;
    //
    //    public HanLPTokenizer(Segment segment) {
    //        this.segment = segment;
    //        this.termList = new LinkedList <com.hankcs.hanlp.seg.common.Term>();
    //        this.termQueue = new LinkedList <com.hankcs.hanlp.seg.common.Term>();
    //        this.charTermAttr = addAttribute(CharTermAttribute.class);
    //        this.offsetAttr = addAttribute(OffsetAttribute.class);
    //        this.typeAttr = addAttribute(TypeAttribute.class);
    //    }
    //
    //    @Override
    //    public boolean incrementToken() throws IOException {
    //        clearAttributes();
    //        if (termQueue.isEmpty()) {
    //            int length = input.read();
    //            if (length == -1) {
    //                return false;
    //            }
    //            char[] buffer = new char[length];
    //            input.read(buffer);
    //            String text = new String(buffer);
    //            termList.clear();
    //            termList.addAll(segment.seg(text));
    //            termQueue.addAll(termList);
    //        }
    //
    //        com.hankcs.hanlp.seg.common.Term term = termQueue.poll();
    //        if (term == null) {
    //            return false;
    //        }
    //        charTermAttr.setEmpty().append(term.word);
    //        offsetAttr.setOffset(term.offset, term.offset + term.word.length());
    //        typeAttr.setType(term.nature.toString());
    //        return true;
    //    }
    //
    //    @Override
    //    public void reset() throws IOException {
    //        super.reset();
    //        termQueue.clear();
    //    }
    //
    //    @Override
    //    public void end() throws IOException {
    //        super.end();
    //        offsetAttr.setOffset(correctOffset(termQueue.peek().offset), correctOffset(termQueue.peek().offset + termQueue.peek().word.length()));
    //    }
    //}
    //
    //public class HanLPAnalyzer extends Analyzer {
    //
    //    @Override
    //    protected TokenStreamComponents createComponents(String fieldName) {
    //        Tokenizer tokenizer = new HanLPTokenizer();
    //        return new TokenStreamComponents(tokenizer);
    //    }
    //
    //    private  class HanLPTokenizer extends Tokenizer {
    //        private final CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);
    //        private final Segment segment = HanLP.newSegment();
    //        private List <Term> termList;
    //        private int termIndex;
    //
    //        @Override
    //        public boolean incrementToken() throws IOException {
    //            clearAttributes();
    //            if (termList == null) {
    //                char[] buffer = new char[1024];
    //                int length = input.read(buffer);
    //                if (length == -1) {
    //                    return false;
    //                }
    //                String text = new String(buffer, 0, length);
    //                termList = segment.seg(text);
    //                termIndex = 0;
    //            }
    //            if (termIndex < termList.size()) {
    //                com.hankcs.hanlp.seg.common.Term term = termList.get(termIndex++);
    //                charTermAttribute.append(term.word);
    //                charTermAttribute.setLength(term.word.length());
    //                return true;
    //            }
    //            return false;
    //        }
    //    }
    //}

    //@SneakyThrows
    //@PostMapping(value = "/test")
    //public void test(String queryStr) {
    //    Directory itemManagementIndex = new RAMDirectory();
    //
    //    // 使用自定义的 HanLPAnalyzer 进行分词
    //    Analyzer analyzer = new HanLPAnalyzer();
    //
    //    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    //    IndexWriter writer = new IndexWriter(itemManagementIndex, config);
    //
    //    // 添加一些示例文档
    //    addDocDefault(writer, "文本示例", "这是一个测试文本，用于演示HanLP的分词功能。");
    //    addDocDefault(writer, "另一个示例", "Lucene是一个全文检索库。");
    //
    //    writer.close();
    //
    //    // 查询示例
    //    //String queryString = "测试"; // 查询关键词
    //    Query query = new QueryParser("content", analyzer).parse(queryStr);
    //
    //    IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(itemManagementIndex));
    //    TopDocs docs = searcher.search(query, 10);
    //    ScoreDoc[] hits = docs.scoreDocs;
    //
    //    // 处理查询结果
    //    System.out.println("查询结果总数：" + hits.length);
    //    for (ScoreDoc hit : hits) {
    //        int docId = hit.doc;
    //        Document d = searcher.doc(docId);
    //        System.out.println("content: " + d.get("content"));
    //    }
    //}



}
