package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: kavanLi-R7000
 * @create: 2024-07-24 14:30
 * To change this template use File | Settings | File and Code Templates.
 */
@Configuration
public class LuceneConfig {

    public static final String TAG_1 = "appId";
    public static final String TAG_2 = "bizUserId";
    public static final String NAME = "name";
    public static final String DESC = "description";
    private List <Document> itemManagementDocs = new ArrayList <>();
    private List <Document> tempDocs = new ArrayList <>();
    /**
     * 每隔100条存储进索引，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private Directory itemManagementIndex;
    private Directory tempIndex;
    private Analyzer analyzer;
    private IndexWriter itemManagementWriter;
    private IndexWriter tempWriter;

    //@Autowired
    //private SmartyunstItemManagementService itemManagementService;


    /**
     * 在Lucene中，如果你有多个类别或不同维度的文档内容，可以选择以下几种方法来分类和分区：
     * 在不同的内存区域放入不同的文档，让Lucene维护多个索引。然后在查询时，选择对应的索引进行查询。这种方法适合类别或维度之间的关系不紧密，或者需要隔离管理的情况。
     */

    @PostConstruct
    public void init() throws Exception {
        //定义分词器
        analyzer = new StandardAnalyzer();

        // 初始化款项索引
        itemManagementIndex = new RAMDirectory();
        IndexWriterConfig itemManagementConfig = new IndexWriterConfig(analyzer);
        itemManagementWriter = new IndexWriter(itemManagementIndex, itemManagementConfig);
        addItemManagementDocs(itemManagementWriter, itemManagementDocs);
        // ------------- 在 Lucene 中，IndexWriter 是设计为长时间运行的，它在整个应用程序生命周期中保持打开状态是常见的实践。中途不关闭 IndexWriter 没有问题，实际上这也是推荐的方式，因为频繁打开和关闭 IndexWriter 会影响性能并增加资源开销。 -------------
        //itemManagementWriter.close();

        // 用于后续拓展的索引
        tempIndex = new RAMDirectory();
        IndexWriterConfig tempConfig = new IndexWriterConfig(analyzer);
        tempWriter = new IndexWriter(tempIndex, tempConfig);
        addTempDocs(tempWriter, tempDocs);
        //tempWriter.close();


    }

    @PreDestroy
    public void close() throws IOException {
        // ------------- 应用程序生命周期结束时关闭 IndexWriter：使用 @PreDestroy 注解来关闭 IndexWriter。 -------------
        if (itemManagementWriter != null) {
            itemManagementWriter.close();
        }
        if (tempWriter != null) {
            tempWriter.close();
        }
    }

    @Bean
    public Directory itemManagementIndex() {
        return itemManagementIndex;
    }

    @Bean
    public Directory tempIndex() {
        return tempIndex;
    }

    @Bean
    public Analyzer analyzer() {
        return analyzer;
    }

    @Bean
    public IndexWriter itemManagementWriter() {
        return itemManagementWriter;
    }

    @Bean
    public IndexWriter tempWriter() {
        return tempWriter;
    }

    private void addTempDocs(IndexWriter writer, List <Document> documents) throws IOException {
        addTempDoc(writer, documents, "To Kill a Mockingbird", "A novel by Harper Lee", "temp", "novel");
        addTempDoc(writer, documents, "1984", "A novel by George Orwell", "temp", "novel");
        add2Index(writer, documents);
    }

    private void addItemManagementDocs(IndexWriter writer, List <Document> documents) throws IOException {
        //List <SmartyunstItemManagement> smartyunstItemManagementList = itemManagementService.list();
        //for (SmartyunstItemManagement smartyunstItemManagement : smartyunstItemManagementList) {
        //    String itemName = smartyunstItemManagement.getItemName();
        //    String applicationId = smartyunstItemManagement.getApplicationId();
        //    String bizUserId = smartyunstItemManagement.getBizUserId();
        //    addItemManagementDoc(writer,itemManagementDocs, documents, itemName, "", applicationId, bizUserId);
        //}
        addItemManagementDoc(writer,itemManagementDocs, "Apple iPhone", "A smartphone by Apple", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Samsung Galaxy", "A smartphone by Samsung", "itemManagement"
                , "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Google Pixel Nord", "A smartphone by Google",
                "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "OnePlus Nord Nord", "A smartphone by OnePlus",
                "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Sony Xperia Nord", "A smartphone by Sony", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Huawei Mate Nord", "A smartphone by Huawei", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Xiaomi Mi", "A smartphone by Xiaomi", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Nokia Lumia", "A smartphone by Nokia", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Motorola Razr", "A smartphone by Motorola", "itemManagement"
                , "phones");
        addItemManagementDoc(writer,itemManagementDocs, "LG V60", "A smartphone by LG", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Asus Zenfone", "A smartphone by Asus", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Oppo Find", "A smartphone by Oppo", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Vivo X", "A smartphone by Vivo", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Realme Narzo", "A smartphone by Realme", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "HTC U", "A smartphone by HTC", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "ZTE Axon", "A smartphone by ZTE", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Honor View", "A smartphone by Honor", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "BlackBerry Key", "A smartphone by BlackBerry",
                "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Alcatel Idol", "A smartphone by Alcatel", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Lenovo Legion", "A smartphone by Lenovo", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "苹果 MacBook", "苹果公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "华硕 VivoBook", "华硕公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "戴尔 XPS", "戴尔公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "惠普 Pavilion", "惠普公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "联想 ThinkPad", "联想公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "华为 MateBook", "华为公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "三星 Notebook", "三星公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "宏碁 Swift", "宏碁公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "小米 Air", "小米公司生产的笔记本电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "微软 Surface", "微软公司生产的平板电脑", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "亚马逊 Kindle", "亚马逊公司生产的电子书阅读器", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "索尼 Walkman", "索尼公司生产的便携式音乐播放器", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "任天堂 Switch", "任天堂公司生产的游戏机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "索尼 PlayStation", "索尼公司生产的游戏机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "微软 Xbox", "微软公司生产的游戏机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "尼康 D850", "尼康公司生产的数码相机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "佳能 EOS", "佳能公司生产的数码相机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "索尼 Alpha", "索尼公司生产的数码相机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "徕卡 M", "徕卡公司生产的数码相机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "奥林巴斯 OM-D", "奥林巴斯公司生产的数码相机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Xiaomi Mi", "A smartphone by Xiaomi", "itemManagement1",
                "phones1");
        addItemManagementDoc(writer,itemManagementDocs, "LG V60", "A smartphone by LG", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Vivo X", "A smartphone by Vivo", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "HTC U", "A smartphone by HTC", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Apple iPhone", "A smartphone by Apple", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Samsung Galaxy", "A smartphone by Samsung", "itemManagement"
                , "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Google Pixel", "A smartphone by Google", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "OnePlus Nord", "A smartphone by OnePlus", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Sony Xperia", "A smartphone by Sony", "itemManagement",
                "phones");
        // 添加更多文档
        addItemManagementDoc(writer,itemManagementDocs, "Huawei P30", "A smartphone by Huawei", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Nokia Lumia", "A smartphone by Nokia", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Motorola Moto", "A smartphone by Motorola", "itemManagement"
                , "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Xiaomi Redmi", "A smartphone by Xiaomi", "itemManagement2",
                "phones2");
        addItemManagementDoc(writer,itemManagementDocs, "Xiaomi Redmi", "A smartphone by Xiaomi", "itemManagement2",
                "phones2");
        addItemManagementDoc(writer,itemManagementDocs, "Asus Zenfone", "A smartphone by Asus", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Oppo Reno", "A smartphone by Oppo", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Realme 7", "A smartphone by Realme", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "ZTE Axon", "A smartphone by ZTE", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Lenovo Legion", "A smartphone by Lenovo", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Honor Magic", "A smartphone by Honor", "itemManagement",
                "phones");
        addItemManagementDoc(writer,itemManagementDocs, "Meizu 16", "A smartphone by Meizu", "itemManagement",
                "phones");
        // 添加中文文档
        addItemManagementDoc(writer,itemManagementDocs, "苹果 iPhone", "苹果公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "华为 P30", "华为公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "小米 10", "小米公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "OPPO R15", "OPPO公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "vivo X27", "vivo公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "魅族 16", "魅族公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "一加 7", "一加公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "中兴 Axon", "中兴公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "联想 Z6", "联想公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "荣耀 Magic", "荣耀公司生产的智能手机", "itemManagement", "phones");

        addItemManagementDoc(writer,itemManagementDocs, "小米手机", "小米公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "苹果手机", "苹果公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "华为手机", "华为公司生产的智能手机", "itemManagement", "phones");
        addItemManagementDoc(writer,itemManagementDocs, "三星手机", "三星公司生产的智能手机", "itemManagement", "phones");
        add2Index(writer, documents);
    }

    private void addItemManagementDoc(IndexWriter writer, List <Document> documents, String name, String description, String tag1, String tag2) throws IOException {
        // 添加新文档
        Document doc = new Document();
        doc.add(new TextField(NAME, name, Field.Store.YES));
        doc.add(new TextField(DESC, description, Field.Store.YES));
        doc.add(new StringField(TAG_1, tag1, Field.Store.YES));
        doc.add(new StringField(TAG_2, tag2, Field.Store.YES));
        documents.add(doc);
        if (documents.size() >= BATCH_COUNT) {
            add2Index(writer, documents);
            // 清空列表，确保内存管理
            documents.clear();
        }
    }

    private void addTempDoc(IndexWriter writer, List <Document> documents, String name, String description, String tag1, String tag2) throws IOException {
        // 添加新文档
        Document doc = new Document();
        doc.add(new TextField(NAME, name, Field.Store.YES));
        doc.add(new TextField(DESC, description, Field.Store.YES));
        doc.add(new StringField(TAG_1, tag1, Field.Store.YES));
        doc.add(new StringField(TAG_2, tag2, Field.Store.YES));
        documents.add(doc);
        if (documents.size() >= BATCH_COUNT) {
            add2Index(writer, documents);
            // 清空列表，确保内存管理
            documents.clear();
        }
    }

    private void add2Index(IndexWriter writer, List <Document> documents) throws IOException {
        writer.addDocuments(documents);
        writer.commit();
    }

}