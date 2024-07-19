package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
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
public class LuceneController {
    @Autowired
    private JavaMailSender mailSender;

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
    //public void test(String querystr) {
    //    Directory memoryIndex = new RAMDirectory();
    //
    //    // 使用自定义的 HanLPAnalyzer 进行分词
    //    Analyzer analyzer = new HanLPAnalyzer();
    //
    //    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    //    IndexWriter writer = new IndexWriter(memoryIndex, config);
    //
    //    // 添加一些示例文档
    //    addDocDefault(writer, "文本示例", "这是一个测试文本，用于演示HanLP的分词功能。");
    //    addDocDefault(writer, "另一个示例", "Lucene是一个全文检索库。");
    //
    //    writer.close();
    //
    //    // 查询示例
    //    //String queryString = "测试"; // 查询关键词
    //    Query query = new QueryParser("content", analyzer).parse(querystr);
    //
    //    IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(memoryIndex));
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

    @PostMapping(value = "")
    public List <String> test1(String querystr) {
        List<String> results = new ArrayList<>();
        try {
            // 使用 StandardAnalyzer 作为分词器
            //Analyzer analyzer = new SmartChineseAnalyzer();
            Analyzer analyzer = new StandardAnalyzer();
            //StandardAnalyzer analyzer = new StandardAnalyzer();
            //CustomAnalyzer analyzer = new CustomAnalyzer();

            // 创建内存索引
            Directory memoryIndex = new RAMDirectory();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            IndexWriter writer = new IndexWriter(memoryIndex, config);
            addDocs(writer);
            writer.close();

            // 创建查询
            //JiebaSegmenter segmenter = new JiebaSegmenter();
            //List<SegToken> tokens = segmenter.process(querystr, JiebaSegmenter.SegMode.SEARCH);
            //StringBuilder sb = new StringBuilder();
            //for (SegToken token : tokens) {
            //    sb.append(token.word).append(" ");
            //}
            //String segmentedQuery = sb.toString().trim();
            //Query q = new PrefixQuery(new Term("name", segmentedQuery.toLowerCase()));
            //Query q = new QueryParser("name", analyzer).parse(segmentedQuery.toLowerCase());
            //Query q = new FuzzyQuery((new Term("name", segmentedQuery.toLowerCase())), BooleanClause.Occur.SHOULD);

            // 创建查询

            Query q = createCombinedQuery(querystr, analyzer, "", "");
            //Query q = new PrefixQuery(new Term("name", querystr.toLowerCase()));
            //Query q = new QueryParser("name", analyzer).parse(querystr);

            // 执行查询
            int hitsPerPage = 10;
            DirectoryReader reader = DirectoryReader.open(memoryIndex);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            // 显示查询结果
            System.out.println("Found " + hits.length + " hits.");
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                results.add((i + 1) + ". " + d.get("name") + "\t" + d.get("description") + "\t" + d.get("appId") +
                        "\t" + d.get("bizUserId"));
                log.info((i + 1) + ". " + d.get("name") + "\t" + d.get("description") + "\t" + d.get("appId") + "\t" + d.get("bizUserId"));
            }
            reader.close();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return results;
    }

    private Query createCombinedQuery(String querystr, Analyzer analyzer, String appId, String bizUserId) throws Exception {
        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        // 使用 Jieba 分词
        //JiebaSegmenter segmenter = new JiebaSegmenter();
        //List<SegToken> tokens = segmenter.process(querystr, JiebaSegmenter.SegMode.SEARCH);
        //StringBuilder sb = new StringBuilder();
        //for (SegToken token : tokens) {
        //    sb.append(token.word).append(" ");
        //}
        //String segmentedQuery = sb.toString().trim();

        // ------------- 前置条件 -------------
        appId = "electronics";
        //QueryParser level1Parser = new QueryParser("appId", analyzer);
        //Query preset1Query = level1Parser.parse(appId.toLowerCase());
        builder.add(new PrefixQuery(new Term("appId", appId.toLowerCase())), BooleanClause.Occur.MUST);

        bizUserId = "phones";
        //QueryParser level2Parser = new QueryParser("bizUserId", analyzer);
        //Query preset2Query = level2Parser.parse(bizUserId.toLowerCase());
        builder.add(new PrefixQuery(new Term("bizUserId", bizUserId.toLowerCase())), BooleanClause.Occur.MUST);

        // PrefixQuery
        PrefixQuery prefixQuery = new PrefixQuery(new Term("name", querystr.toLowerCase()));
        Query prefixBoostedQuery = new BoostQuery(prefixQuery, 2.0f); // 设置较高的权重

        // QueryParser
        Query queryParserQuery = new QueryParser("name", analyzer).parse(querystr.toLowerCase());
        Query queryParserBoostedQuery = new BoostQuery(queryParserQuery, 1.5f); // 设置中等权重

        // FuzzyQuery
        //FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("name", querystr.toLowerCase()));
        //Query fuzzyBoostedQuery = new BoostQuery(fuzzyQuery, 0.5f); // 设置较低的权重

        builder.add(prefixBoostedQuery, BooleanClause.Occur.SHOULD);
        builder.add(queryParserBoostedQuery, BooleanClause.Occur.SHOULD);
        //builder.add(fuzzyBoostedQuery, BooleanClause.Occur.SHOULD);

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

    public static final String TAG_1 = "appId";
    public static final String TAG_2 = "bizUserId";



    private void addDoc(IndexWriter writer, String name, String description, String tag1, String tag2) throws Exception {
        Document doc = new Document();
        doc.add(new TextField("name", name, Field.Store.YES));
        doc.add(new TextField("description", description, Field.Store.YES));
        doc.add(new TextField("appId", tag1, Field.Store.YES));
        doc.add(new TextField("bizUserId", tag2, Field.Store.YES));
        writer.addDocument(doc);
    }

    @SneakyThrows
    private void addDocs(IndexWriter writer) throws IOException {
        addDoc(writer, "Apple iPhone", "A smartphone by Apple", "electronics", "phones");
        addDoc(writer, "Samsung Galaxy", "A smartphone by Samsung", "electronics", "phones");
        addDoc(writer, "Google Pixel Nord", "A smartphone by Google", "electronics", "phones");
        addDoc(writer, "OnePlus Nord Nord", "A smartphone by OnePlus", "electronics", "phones");
        addDoc(writer, "Sony Xperia Nord", "A smartphone by Sony", "electronics", "phones");
        addDoc(writer, "Huawei Mate Nord", "A smartphone by Huawei", "electronics", "phones");
        addDoc(writer, "Xiaomi Mi", "A smartphone by Xiaomi", "electronics", "phones");
        addDoc(writer, "Nokia Lumia", "A smartphone by Nokia", "electronics", "phones");
        addDoc(writer, "Motorola Razr", "A smartphone by Motorola", "electronics", "phones");
        addDoc(writer, "LG V60", "A smartphone by LG", "electronics", "phones");
        addDoc(writer, "Asus Zenfone", "A smartphone by Asus", "electronics", "phones");
        addDoc(writer, "Oppo Find", "A smartphone by Oppo", "electronics", "phones");
        addDoc(writer, "Vivo X", "A smartphone by Vivo", "electronics", "phones");
        addDoc(writer, "Realme Narzo", "A smartphone by Realme", "electronics", "phones");
        addDoc(writer, "HTC U", "A smartphone by HTC", "electronics", "phones");
        addDoc(writer, "ZTE Axon", "A smartphone by ZTE", "electronics", "phones");
        addDoc(writer, "Honor View", "A smartphone by Honor", "electronics", "phones");
        addDoc(writer, "BlackBerry Key", "A smartphone by BlackBerry", "electronics", "phones");
        addDoc(writer, "Alcatel Idol", "A smartphone by Alcatel", "electronics", "phones");
        addDoc(writer, "Lenovo Legion", "A smartphone by Lenovo", "electronics", "phones");
        addDoc(writer, "苹果 MacBook", "苹果公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "华硕 VivoBook", "华硕公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "戴尔 XPS", "戴尔公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "惠普 Pavilion", "惠普公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "联想 ThinkPad", "联想公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "华为 MateBook", "华为公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "三星 Notebook", "三星公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "宏碁 Swift", "宏碁公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "小米 Air", "小米公司生产的笔记本电脑", "electronics", "phones");
        addDoc(writer, "微软 Surface", "微软公司生产的平板电脑", "electronics", "phones");
        addDoc(writer, "亚马逊 Kindle", "亚马逊公司生产的电子书阅读器", "electronics", "phones");
        addDoc(writer, "索尼 Walkman", "索尼公司生产的便携式音乐播放器", "electronics", "phones");
        addDoc(writer, "任天堂 Switch", "任天堂公司生产的游戏机", "electronics", "phones");
        addDoc(writer, "索尼 PlayStation", "索尼公司生产的游戏机", "electronics", "phones");
        addDoc(writer, "微软 Xbox", "微软公司生产的游戏机", "electronics", "phones");
        addDoc(writer, "尼康 D850", "尼康公司生产的数码相机", "electronics", "phones");
        addDoc(writer, "佳能 EOS", "佳能公司生产的数码相机", "electronics", "phones");
        addDoc(writer, "索尼 Alpha", "索尼公司生产的数码相机", "electronics", "phones");
        addDoc(writer, "徕卡 M", "徕卡公司生产的数码相机", "electronics", "phones");
        addDoc(writer, "奥林巴斯 OM-D", "奥林巴斯公司生产的数码相机", "electronics", "phones");
        addDoc(writer, "Xiaomi Mi", "A smartphone by Xiaomi", "electronics1", "phones1");
        addDoc(writer, "LG V60", "A smartphone by LG", "electronics", "phones");
        addDoc(writer, "Vivo X", "A smartphone by Vivo", "electronics", "phones");
        addDoc(writer, "HTC U", "A smartphone by HTC", "electronics", "phones");
        addDoc(writer, "Apple iPhone", "A smartphone by Apple", "electronics", "phones");
        addDoc(writer, "Samsung Galaxy", "A smartphone by Samsung", "electronics", "phones");
        addDoc(writer, "Google Pixel", "A smartphone by Google", "electronics", "phones");
        addDoc(writer, "OnePlus Nord", "A smartphone by OnePlus", "electronics", "phones");
        addDoc(writer, "Sony Xperia", "A smartphone by Sony", "electronics", "phones");
        // 添加更多文档
        addDoc(writer, "Huawei P30", "A smartphone by Huawei", "electronics", "phones");
        addDoc(writer, "Nokia Lumia", "A smartphone by Nokia", "electronics", "phones");
        addDoc(writer, "Motorola Moto", "A smartphone by Motorola", "electronics", "phones");
        addDoc(writer, "Xiaomi Redmi", "A smartphone by Xiaomi", "electronics2", "phones2");
        addDoc(writer, "Asus Zenfone", "A smartphone by Asus", "electronics", "phones");
        addDoc(writer, "Oppo Reno", "A smartphone by Oppo", "electronics", "phones");
        addDoc(writer, "Realme 7", "A smartphone by Realme", "electronics", "phones");
        addDoc(writer, "ZTE Axon", "A smartphone by ZTE", "electronics", "phones");
        addDoc(writer, "Lenovo Legion", "A smartphone by Lenovo", "electronics", "phones");
        addDoc(writer, "Honor Magic", "A smartphone by Honor", "electronics", "phones");
        addDoc(writer, "Meizu 16", "A smartphone by Meizu", "electronics", "phones");
        // 添加中文文档
        addDoc(writer, "苹果 iPhone", "苹果公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "华为 P30", "华为公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "小米 10", "小米公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "OPPO R15", "OPPO公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "vivo X27", "vivo公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "魅族 16", "魅族公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "一加 7", "一加公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "中兴 Axon", "中兴公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "联想 Z6", "联想公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "荣耀 Magic", "荣耀公司生产的智能手机", "electronics", "phones");

        addDoc(writer, "小米手机", "小米公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "苹果手机", "苹果公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "华为手机", "华为公司生产的智能手机", "electronics", "phones");
        addDoc(writer, "三星手机", "三星公司生产的智能手机", "electronics", "phones");
    }


}
