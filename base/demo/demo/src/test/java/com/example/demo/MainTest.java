package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.example.demo.constant.utils.GuavaCacheUtil;
import com.example.demo.domain.User;
import com.example.demo.domain.entity.MapperTestEO;
import com.example.demo.domain.model.MapperTestVO;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.driver.OracleConnection;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.MDC;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 11:51
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public class MainTest {

    /* fields -------------------------------------------------------------- */
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /* fields -------------------------------------------------------------- */

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */
    public String formatDateToString(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date _date = new Date(date);
        String formatDate = simpleDateFormat.format(_date);
        return formatDate;

    }

    @NotNull
    public static Object formatDateToString1(@NotNull Object date) {
        if ((Boolean) date) {
        }
        return date;

    }

    public static List strToList(String s) {
        return Arrays.asList(s);

    }

    /* private methods ----------------------------------------------------- */
    private static Pattern LETTER_PATTERN = Pattern.compile("[a-zA-z]");
    private static Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("^\\d+$");
    private static Pattern POSITIVE_NUMBER_PATTERN2 = Pattern.compile("(.*?)\\d(.*?)");
    private static Pattern FOOBAR = Pattern.compile("BAT-26:(-);");
    private static Pattern NUMBER_PATTERN = Pattern.compile("[^0-9]");
    private static Pattern NUMBER_PATTERN1 = Pattern.compile("[0-9]");

    private static int i = 100;
    //private static Object lock = new Object();

    private static ReentrantLock lock = new ReentrantLock(true);


    /* getters/setters ----------------------------------------------------- */






    public static void main(String[] args) throws Exception {

        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));

        //String reqStr = "Go to Zibo for barbecue";
        //System.out.println(Thread.currentThread().getName() + " 开始请求 " + reqStr);
        //WebClient webClient = WebClient.create("http://localhost:8080");
        //
        //Mono <String> stringMono = webClient.get()
        //        .uri("http://localhost:8080/test")
        //        //.body(BodyInserters.fromValue(reqStr))
        //        .retrieve()
        //        .bodyToMono(String.class);
        //
        //stringMono.subscribe(result -> {
        //    System.out.println("返回参数: " + result);
        //}, error -> {
        //    System.err.println("发生错误: " + error.getMessage());
        //}, () -> {
        //    System.out.println("请求完成");
        //});
        //
        //System.out.println("stringMono: " + stringMono.subscribe(res ->
        //                        System.out.println(Thread.currentThread().getName() + " webClient收到应答 " + res)));
        //HttpClient httpClient = HttpClient.create().port(8080);
        //ByteBufFlux byteBufFlux = ByteBufFlux.fromString(Flux.just(reqStr));
        //httpClient.post()               // Specifies that POST method will be used
        //        .uri("/test/world")   // Specifies the path
        //        .send(byteBufFlux)  // Sends the request body
        //        .responseContent()    // Receives the response body
        //        .aggregate()
        //        .asString()
        //        .subscribe(res ->
        //                System.out.println(Thread.currentThread().getName() + "httpClient收到应答 " + res));
        //
        //List <Integer> collect = Stream.of(1, 2, 3).collect(Collectors.toList());
        //collect.add(4);
        //MDC.put("traceId", "666666666");
        //log.info("Processing request...");
        //MDC.clear();
        //
        //// 创建随机数生成器
        //Random random = new Random();

        //for (int j = 0; j < 1000; j++) {
        //    // 生成随机数，范围为0到1
        //    int randomNumber = random.nextInt(3);
        //
        //    // 输出随机数
        //    System.out.println(randomNumber);
        //
        //}


        //Short short1 = true && false;
        //final String fs = "abcv";
        //System.out.println(fs + "_" + 234243L);
        //JSONObject bodyJsonObj = new JSONObject();
        //bodyJsonObj.put("acctNo", "123123");
        //User user = new User();
        //user.setBODY(bodyJsonObj);
        //JSONObject dataJson = new JSONObject();
        //JSONObject head = new JSONObject();
        //head.put("PdVerNo", "V1");
        //head.put("APIVerNo", "1.0");
        //dataJson.put("Head", head);
        //Runnable decrementTask = () -> {
        //    while (true) {
        //        lock.lock(); // 加锁
        //        try {
        //            if (i == 0) {
        //                break; // 当 i 变为 0 时退出循环
        //            }
        //            i--; // 减一操作
        //            System.out.println(Thread.currentThread().getName() + " - i: " + i);
        //        } finally {
        //            lock.unlock(); // 释放锁
        //        }
        //    }
        //};
        //
        //Thread thread1 = new Thread(decrementTask);
        //Thread thread2 = new Thread(decrementTask);
        //thread1.start();
        //thread2.start();
        //thread1.join();
        //thread2.join();
        //
        //System.out.println("Done!");

        /**
         * modelMapper
         * 性能优化：
         * ModelMapper： 在一些场景中，可能比 BeanUtils 更高效，因为它使用了内部的缓存机制，并且有一些优化手段。
         * BeanUtils： 基于反射，可能在性能上不如 ModelMapper。
         *
         * 支持嵌套映射：
         * ModelMapper： 可以更容易地进行深度嵌套映射，即对象中包含其他对象的情况。
         * BeanUtils： 在处理嵌套对象时可能需要编写更多的自定义代码。
         */
        ModelMapper modelMapper = new ModelMapper();
        TypeMap <MapperTestEO, MapperTestVO> propertyMapper = modelMapper.createTypeMap(MapperTestEO.class, MapperTestVO.class);
        // 设置字段名不一致的情况
        propertyMapper.addMapping(MapperTestEO::getUid, MapperTestVO::setUuid);
        MapperTestEO mapperTestEO = new MapperTestEO();
        mapperTestEO.setUid(1L);
        mapperTestEO.setName("2");
        mapperTestEO.setAge(3);
        mapperTestEO.setEmail("4");
        mapperTestEO.setGender("5");
        MapperTestVO mapperTestVO = modelMapper.map(mapperTestEO, MapperTestVO.class);
        System.out.println("------------------------------------------------------------------");

        Map <Long, String> allEnumList = GuavaCacheUtil.getAllEnumList();
        //throw new IllegalArgumentException();
        Thread.sleep(1000);
        Map <Long, String> allEnumList1 = GuavaCacheUtil.getAllEnumList();
        System.out.println(123);
        //new Thread(() -> {
        //    System.out.println(123);
        //}).start();
        //
        //for (int i = 0; i < 100; i++) {
        //    new Thread(() -> {
        //        try {
        //            Thread.sleep(10000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //    }).start();
        //}
        //String _otherPathologicalNumber = StringUtils.substringBetween("送检切片编号:23-sdf67 ;HE", "送检切片编号:", " ").trim();
        //DemoData demoData = new DemoData();
        //DemoData demoData1 = new DemoData();
        //demoData.set_260And280("123123");
        //demoData.setA260("123123");
        //demoData.setA280("123123");
        //demoData.setDataAndTime("123123");
        //demoData.setNucleicAcidConc("123123");
        //demoData.setSampleId("123123");
        //demoData.setUserName("123123");
        //Bar bar = new Bar();
        //bar.setAge(123);
        //bar.setName("test");
        //demoData.setBar(bar);
        //
        //demoData1 = new DemoData();
        //demoData1.set_260And280("6666");
        //demoData1.setA260("6666");
        //demoData1.setA280("6666");
        //demoData1.setDataAndTime("6666");
        //demoData1.setNucleicAcidConc("6666");
        //demoData1.setSampleId("6666");
        //demoData1.setUserName("6666");
        //
        //String[] properties = {"sampleId", "userName"};
        //BeanUtils.copyProperties(demoData, demoData1, EditDemoData.class);
        //
        //
        //String percentage = "100.00%";
        //NumberFormat nf = NumberFormat.getPercentInstance();
        //Number m = nf.parse(percentage);
        //double decimal = m.doubleValue();
        //Double num = (Double)NumberFormat.getPercentInstance().parse("67.89%");
        //int i1 = StringUtils.lastIndexOf("sdfd数目1、", "1、");
        //int i12 = StringUtils.lastIndexOf("sdfd数目1、", "数目");
        //
        //String 数目 = StringUtils.substringBetween("sdfd数目1、", "1、", "数目").trim();
        //String aa = "";
        //String s4 = aa.replaceAll("\\s", "");
        //String s3 = StringUtils.substringAfterLast("NM_018557:exon55:c.G8793C:p.L2931F", ":");
        //String format = DateFormatUtils.format(new Date(), "yyyyMMdd-HH:mm");
        //
        //Map <String, List<String>> reportSomaticResultMap = new TreeMap <>(new Comparator <String>() {
        //
        //    /*
        //     * int compare(Object o1, Object o2) 返回一个基本类型的整型，
        //     * 返回负数表示：o1 小于o2，
        //     * 返回0 表示：o1和o2相等，
        //     * 返回正数表示：o1大于o2。
        //     */
        //    @Override
        //    public int compare(String o1, String o2) {
        //        return o1.compareTo(o2);
        //        // if(SomaticSortEnum.contains(o1)&&SomaticSortEnum.contains(o2)){
        //        // SomaticSortEnum s1 = SomaticSortEnum.valueOf(o1);
        //        // SomaticSortEnum s2 = SomaticSortEnum.valueOf(o2);
        //        // return s1.getOrder()-s2.getOrder();
        //        // }else{
        //        // return 1;
        //        // }
        //    }
        //});
        //List<String> reportGermlineResultList = reportSomaticResultMap.get(null);
        //if (null == reportGermlineResultList) {
        //    reportGermlineResultList = new ArrayList <>();
        //}
        //List <String> reportIdList = Arrays.asList("1", "2", "3");
        //List <String> reportIdList1 = Arrays.asList("1", "3");
        //reportIdList.removeAll(reportIdList1);
        //Iterator <String> reportIdListIterator = reportIdList.iterator();
        //while (reportIdListIterator.hasNext()) {
        //    String reportId = reportIdListIterator.next();
        //    reportIdList.remove(reportId);
        //    System.out.println(reportId);
        //}
        //ReentrantLock reentrantLock = new ReentrantLock();

        //List <String> collect = Stream.of("PM2104390B_MEN1_pic1.jpeg", "PM2104390B_NF1_pic5.jpeg", "PM2104390B_NF1_pic4.jpeg",
        //        "PM2104390B_RET_pic3.jpeg", "PM2104390B_VHL_pic2.jpeg").sorted(Comparator.comparing(e -> StringUtils.substringAfterLast(e, "_"))).collect(Collectors.toList());
        //String s = StringUtils.substringAfterLast("PM2104390B_MEN1_pic1.jpeg", "_");
        ////.sorted(Comparator.comparing(MutationSite::getGene)).collect(Collectors.toList());
        //String asdfsdf1 = "\\gcbi\\storage\\gcsas\\sequencing_result\\2019\\2\\MP2020-00004\\MP2020-00004-0.bam";
        //String sequencing_result = StringUtils.substringAfter(asdfsdf1, "sequencing_result").replace("\\", "/");
        //String asdfsdf = "PM180039B_MEN1_pic1.jpg";
        //String sddddddd = StringUtils.substringBetween(asdfsdf, "_", "_");
        //String join = StringUtils.join(Arrays.asList("图", 3, ": ", "gene", "基因MLPA检测结果图"), "");
        //List <String> existMPNumberList = Arrays.asList("1", "2", "3");
        //List <String> newAddMPNumberList =
        //        Arrays.asList("1", "2", "3", "6").stream().filter(e -> !existMPNumberList.contains(e)).collect(Collectors.toList());
        //String prefix = "xcdsf " + "\\\\\\\\n";
        //String[] fileNameList = new String[0];
        //for (String fileName : Optional.ofNullable(fileNameList).orElse(new String[0])) {
        //    System.out.println(123);
        //}
        //Optional <Object> o = Optional.ofNullable(null);
        //Object o1 = o.orElse("");
        //MolecularPathology molecularPathology = new MolecularPathology();
        //molecularPathology.setMolecularPathologyNumber("KY9999999");
        //if (Stream.of("MP", "DM").anyMatch(e -> StringUtils.startsWithIgnoreCase("MP12312gh", e))) {
        //    // 临床
        //    molecularPathology.setMpCategory(MPCategory.clinical);
        //}
        //if (Stream.of("KY", "CT", "QC").anyMatch(e -> StringUtils.startsWithIgnoreCase("QCKYCT12312gh", e))) {
        //    // 科研
        //    molecularPathology.setMpCategory(MPCategory.research);
        //}
        //MPCategory sdfcv = MPCategory.valueOf("clinical");
        //SimpleDateFormat simpleDateFormat23244 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat simpleDateFormat123 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date6 = new Date();
        //Date s1 = simpleDateFormat123.parse(simpleDateFormat23244.format(date6)+" 00:00:00");
        //
        //Date s2 = simpleDateFormat123.parse(simpleDateFormat23244.format(date6)+" 23:59:59");

        //String fixFilePath = StringUtils.replace("/gcbi/storage/gcsas/dsfsdfsdfsfd", "/gcbi/storage/gcsas/", "/data" +
        //        "/nfs/");
        //HashMap <String, String> testTagToFileTargetMap = new HashMap <>();
        //Stream.of("二代-41基因", "二代-520基因", "二代-520基因v2", "二代-53基因", "二代-56基因", "二代-63基因", "二代-68基因",
        //        "二代-HRD", "二代-ct108基因").forEach(e -> testTagToFileTargetMap.put(e, "123"));
        //Stream.of("二代-RJ28基因", "二代-RJ28基因D", "二代-RJ28基因R").forEach(e -> testTagToFileTargetMap.put(e, "123232"));
        //Stream.of("二代-SH481基因").forEach(e -> testTagToFileTargetMap.put(e, "1234"));
        //Stream.of("二代-79基因").forEach(e -> testTagToFileTargetMap.put(e, "123rsdf"));

        //Date yearOfDate = DateUtils.setYears(date6, 2018);
        //DateTime dateTime2 = new DateTime(yearOfDate);
        //DateTime dateTime4 =
        //        dateTime2.withYear(dateTime2.getYear()).plusYears(1).monthOfYear().setCopy(1).dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        //DateTime dateTime3 = dateTime2.year().withMinimumValue().withTimeAtStartOfDay();
        //DateTime timeAtEndOfYear = dateTime2.year().withMaximumValue().withTimeAtStartOfDay();
        //Date date5 = timeAtEndOfYear.toDate();
        //Date date4 = dateTime3.toDate();
        //Date date = date6;
        //List <Integer> collect2 = Stream.of(1, 2, 3, 4, 3, 2, 1).map(e -> e + 1).distinct().collect(Collectors.toList());
        //
        //
        //File file = new File("");
        //boolean exists = file.exists();


        //BmpsWorkFlowInfo bmpsWorkFlowInfo = JSON.parseObject("{\n" +
        //        "        \"caseCode\": \"MP2020-00002-R\",\n" +
        //        "        \"projectId\": \"67f5247b5dc14ddfabf0c8785f4c6553\",\n" +
        //        "        \"qcList\": [\n" +
        //        "            {\n" +
        //        "                \"qc\": {\n" +
        //        "                    \"PF Q30 bases/PF Bases\": \"0.868049\",\n" +
        //        "                    \"PCT PF reads aligned\": \"0.5\",\n" +
        //        "                    \"PCT traget bases 200x\": \"0.0\",\n" +
        //        "                    \"PCT traget bases 500x\": \"0.0\",\n" +
        //        "                    \"PF reads\": \"16\",\n" +
        //        "                    \"Mean insert size\": \"87.75\",\n" +
        //        "                    \"Mean amplicon coverage\": null,\n" +
        //        "                    \"PCT traget bases 1000x\": \"0.0\",\n" +
        //        "                    \"PF HQ aligned Q20 bases\": \"656\",\n" +
        //        "                    \"Target bases\": \"0.000000\",\n" +
        //        "                    \"PF Q20 bases/PF Bases\": \"0.919616\",\n" +
        //        "                    \"PF bases\": \"1978\",\n" +
        //        "                    \"Median insert size\": \"88\",\n" +
        //        "                    \"Mean target coverage\": \"0\",\n" +
        //        "                    \"Sequencing uniformity\": \"0.0\",\n" +
        //        "                    \"PCT reads aligned in pairs\": \"1\",\n" +
        //        "                    \"PCT traget bases 1x\": \"0.0\",\n" +
        //        "                    \"PCT traget bases 30x\": \"0.0\",\n" +
        //        "                    \"PCT traget bases 20x\": \"0.0\",\n" +
        //        "                    \"Median target coverage\": \"0\",\n" +
        //        "                    \"On target from pair bases\": null,\n" +
        //        "                    \"On target bases\": \"0\",\n" +
        //        "                    \"PCT traget bases 10x\": \"0.0\",\n" +
        //        "                    \"Mean bait coverage\": \"0\",\n" +
        //        "                    \"Target territory\": \"1498688\",\n" +
        //        "                    \"PCT traget bases 100x\": \"0.0\",\n" +
        //        "                    \"PCT traget bases 300x\": \"0.0\",\n" +
        //        "                    \"PF Q30 bases\": \"1717\",\n" +
        //        "                    \"PF Q20 bases\": \"1819\",\n" +
        //        "                    \"PCT traget bases 50x\": \"0.0\",\n" +
        //        "                    \"Reads aligned in pairs\": \"8\"\n" +
        //        "                },\n" +
        //        "                \"accession\": \"MP2020-00002-R-0\"\n" +
        //        "            }\n" +
        //        "        ],\n" +
        //        "        \"resultFileList\": [\n" +
        //        "            {\n" +
        //        "                \"accession\": \"MP2020-00002-R-0\",\n" +
        //        "                \"filePath\": \"file:///gcbi/storage/gcsas/gcsas-workflow/FLOW-18f87277f2c1406ab0c7513178b23add/starFusion/seq_sorted_bam_file-MP2020-00002-R-0_star_sorted.bam\",\n" +
        //        "                \"type\": \"seq_sorted_bam_file\",\n" +
        //        "                \"values\": null\n" +
        //        "            },\n" +
        //        "            {\n" +
        //        "                \"accession\": \"MP2020-00002-R-0\",\n" +
        //        "                \"filePath\": \"file:///gcbi/storage/gcsas/gcsas-workflow/FLOW-18f87277f2c1406ab0c7513178b23add/starFusion/seq_bam_index_file-MP2020-00002-R-0_star_sorted.bam.bai\",\n" +
        //        "                \"type\": \"seq_bam_index_file\",\n" +
        //        "                \"values\": null\n" +
        //        "            },\n" +
        //        "            {\n" +
        //        "                \"accession\": \"MP2020-00002-R-0\",\n" +
        //        "                \"filePath\": \"file:///gcbi/storage/gcsas/gcsas-workflow/FLOW-18f87277f2c1406ab0c7513178b23add/starFusion/seq_rna_fusion-MP2020-00002-R-0star_fusion_anno.txt\",\n" +
        //        "                \"type\": \"seq_rna_fusion\",\n" +
        //        "                \"values\": null\n" +
        //        "            }\n" +
        //        "        ],\n" +
        //        "        \"analyzeStatus\": \"succ\",\n" +
        //        "        \"workFlowId\": \"FLOW-18f87277f2c1406ab0c7513178b23add\"\n" +
        //        "    }", BmpsWorkFlowInfo.class);

        /**
         * 压缩图片
         */
        // 压缩图片
//        BufferedImage readImage = ImageIO.read(new File("/0.jpg"));
//
//        File compressedImageFile = new File("C:\\Users\\kavanLi\\Desktop\\0compressed.jpg");
//        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
//        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//        jpegParams.setCompressionQuality(0.5f);
//        final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
//        // specifies where the jpg image has to be written
//        writer.setOutput(new FileImageOutputStream(compressedImageFile));
//
//        // writes the file with given compression level
//        // from your JPEGImageWriteParam instance
//        writer.write(null, new IIOImage(readImage, null, null), jpegParams);
//
//        Stream <List <String>> listStream = Stream.of("日期", "FISH", "sanger", "ARMS", "基因重排", "MSI", "HPV", "NGS", "其他").map(Arrays::asList);
//        listStream.collect(Collectors.toList()).stream().flatMap(Collection::stream).forEach(System.out::println);
//        Stream <String> 日期 = Stream.of("日期", "FISH", "sanger", "ARMS", "基因重排", "MSI", "HPV", "NGS", "其他");
//        Optional<String> optional = Optional.ofNullable(null);
//        List<String> list = new ArrayList <>();
//        String s1 = optional.orElseGet(() -> "321");
//        Stream<ArrayList> stream = Stream.of("123", "321").map(s -> {
//            ArrayList <String> objects = new ArrayList <>();
//            objects.add(s);
//            return objects;
//        });
//        List <ArrayList> collect1 = stream.collect(Collectors.toList());
//        DefaultAndStaticMethods defaultAndStaticMethods = () -> System.out.println(123);
//        defaultAndStaticMethods.foobar();
//        String overview = new Bar().getOverview();
//        String producer = DefaultAndStaticMethods.producer();
//        boolean b1 = Stream.of("123", "321").anyMatch(Bar::isRealUser);
//        boolean b13 = Stream.of("123", "321").anyMatch(new Bar()::isLegalName);
//        long count = Stream.of("123", "321").filter(String::isEmpty).count();
//        Stream<Bar> stream2 = Stream.of("123", "321").map(Bar::new);
//        List <Bar> collect = stream2.collect(Collectors.toList());
//        List<Bar> bars = Arrays.asList(new Bar("Foo", 21),
//                new Bar("Bar", 25));
//        bars.sort((o1, o2) -> {
//            return o1.getAge() - o2.getAge();
//        });
//
//        List<String> wordsList = Arrays.asList("123", "312");
//        String inputString = "hello there, Baeldung";
//        String[] words = {"hello", "Baeldung"};
//
//        Trie trie = Trie.builder().onlyWholeWords().addKeywords(words).build();
//        Collection<Emit> emits = trie.parseText(inputString);
//
//        emits.forEach(System.out::println);
//        VerbalExpression build = VerbalExpression.regex().oneOf("EGFR基因Exon18有突变", "EGFR基因Exon19有突变", "EGFR基因Exon20" +
//                "有突变", "EGFR基因Exon21有突变").build();
//        VerbalExpression testRegex2 = VerbalExpression.regex().capt().oneOf("google").build();
//        String s = build.toString();
//// Create an example URL
//        String url = "httpsgoogle://wwaw..coam";
//        String url1 = "httpsgoogle://wwaw..coaEGFR基因Exon18a有突变m";
//
//        boolean b = testRegex2.test(url);//True
//        boolean b2 = build.test(url1);//True
//
//
//        boolean naN = Double.isNaN(0 / 0);
//        boolean naaN = Double.isInfinite(Integer.valueOf(2) / Integer.valueOf(0));
//        System.out.println(123);
        //tableWrite();
        //noModleWrite();
        //readDataOfMSIFromCsvPath("/MP2019-20476_somatic.xls");
        //Bar bar = new Bar();
        //bar.setSize("123123");
        //Object s = formatDateToString1(null);
        //long s1 = System.currentTimeMillis();
        //TimeUnit.SECONDS.sleep(5);
        //long s2 = System.currentTimeMillis();
        //long l = s2 - s1;
        //System.out.println(l);
        //File source = new File("/abc");
        //File dest = new File("/abcd");
        //try {
        //    //FileUtils.copyDirectory(source, dest);
        //    FileUtils.copyDirectory(source, dest, new FileFilter() {
        //        @Override
        //        public boolean accept(File file) {
        //            // 不需要拷贝的文件
        //            if (StringUtils.containsIgnoreCase(file.getParentFile().getName(), "SampleSheet.csv") || StringUtils.containsIgnoreCase(file.getParentFile().getName(), "DispatchComplete")) {
        //                return false;
        //            }
        //            // 文件夹都要拷贝
        //            if (file.isDirectory()) {
        //                return true;
        //            }
        //            // fastq.gz文件拷贝
        //            if (StringUtils.endsWithIgnoreCase(file.getName(), "fastq.gz")) {
        //                return true;
        //            }
        //            // 根目录文件拷贝
        //            if (StringUtils.equalsIgnoreCase(file.getParentFile().getName(), "abc")) {
        //                return true;
        //            }
        //            return false;
        //        }
        //    });
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //
        //
        //String replace = StringUtils.replace("sdfsdf-N", "-N", "");
        //
        //
        //List <String> list = Arrays.asList("$123", "&321");
        //
        //if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
        //    for (int i = 0; i < list.size(); i++) {
        //        list.set(i, StringEscapeUtils.escapeHtml4(list.get(i)));
        //    }
        //    for (String testResult : list) {
        //        StringEscapeUtils.escapeHtml4(testResult);
        //    }
        //}
        //
        //List <String> filedList = new ArrayList <>();
        //List <List <String>> rowDataList = new ArrayList <>();
        //
        //File sopLabXmlFile = new File("/2019.07.16.xml");
        //
        //String name = sopLabXmlFile.getName();
        //String suffix=name.substring(name.lastIndexOf(".")+1, name.length());
        //SAXReader reader = new SAXReader();
        //Document doc = reader.read(sopLabXmlFile);
        //Element root = doc.getRootElement();
        //Element table = root.element("Worksheet").element("Table");
        //List <Element> rowList = (List <Element>) table.elements("Row");
        //// 打日志显示有该xml有多少行。
        //for (int i = 0; i < rowList.size(); i++) {
        //    Element row = rowList.get(i);
        //    List <Element> cellList = (List <Element>) row.elements("Cell");
        //    List <String> rowData = new ArrayList <>();
        //    for (int j = 0; j < cellList.size(); j++) {
        //        Element cell = cellList.get(j);
        //        String data = cell.elementText("Data");
        //        if (null != cell.elementText("Data")) {
        //            rowData.add(data);
        //        }
        //    }
        //    if (i == 0 && CollectionUtils.isNotEmpty(rowData)) {
        //        // 贮存列头
        //        for (int m = 0; m < rowData.size(); m++) {
        //            if (StringUtils.containsIgnoreCase(rowData.get(m), "ID")) {
        //                rowData = rowData.subList(m, rowData.size());
        //                filedList.addAll(rowData);
        //                break;
        //            }
        //        }
        //    } else if (CollectionUtils.isNotEmpty(rowData)){
        //        // 贮存行数据
        //        for (int m = 0; m < rowData.size(); m++) {
        //            if (StringUtils.containsIgnoreCase(rowData.get(m), "MP") || StringUtils.containsIgnoreCase(rowData.get(m), "DM")) {
        //                rowData = rowData.subList(m, rowData.size());
        //                rowDataList.add(rowData);
        //                break;
        //            }
        //        }
        //    }
        //}
        // 保存入数据库

        //System.out.println(filedList.toString());
        System.out.println(132);
        //File sopLabXmlFile = new File("/2019.07.16.xml");
        //SAXReader reader = new SAXReader();
        //Document doc = reader.read(sopLabXmlFile);
        //Element root = doc.getRootElement();
        //Element table = root.element("Worksheet").element("Table");
        //Iterator rowList = table.elementIterator("Row");
        //boolean hasRow = rowList.hasNext();
        //for (Iterator rows = table.elementIterator("Row"); rows.hasNext();) {
        //    Element row = (Element) rows.next();
        //    List <String> rowData = new ArrayList <>();
        //    for (Iterator cells = row.elementIterator("Cell"); cells.hasNext();) {
        //        Element cell = (Element) cells.next();
        //        //if (null != cell.elementText("Data") && (StringUtils.equalsIgnoreCase()))
        //        System.out.print("  " + cell.elementText("Data"));
        //    }
        //    System.out.print("\n");
        //}
        try {

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        //String fileName = "/2019.07.16.xml";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        //EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        //BufferedInputStream in = new BufferedInputStream(new FileInputStream("/2019.07.16.xlsx"));
        //File file =new File("/123.xlsx");
        //BufferedInputStream in = new BufferedInputStream(new FileInputStream("/123.xlsx"));
        //int fileLength=(int)file.length();
        //byte b[]=new byte[fileLength];
        //int length=in.read(b);
        //in.close();
        //System.out.println("读取到的内容是："+new String(b));
        //Scanner scanner = new Scanner(in);
        //System.out.println(scanner.next());
        //EasyExcel.read(in, DemoData.class, new DemoDataListener()).sheet().doRead();
        //Map <String, String> map1 = readDataOfMSIFromCsvPath("/2019.07.16.xml");

        //String sdf = StringUtils.replace("MP2019-11111-N", "-N", "");
        //
        //String a = "#TMB:1.59[2]";
        //String s = StringUtils.substringAfter(a, "TMB:");
        //String s1 = StringUtils.substringBefore(s, "[");
        //String s2 = StringUtils.substringBetween(a, "TMB:", "[");
        //String tmb = null;
        //try {
        //    CsvReader reader = new CsvReader("/MP2019-03983_TMB.csv");
        //    reader.readHeaders();
        //    String[] headers = reader.getHeaders();
        //    while (reader.readRecord()) {
        //        if (StringUtils.containsIgnoreCase(reader.get(0), "TMB")) {
        //            tmb = StringUtils.substringBetween(reader.get(0),  "TMB:","[");
        //            break;
        //        } else {
        //            continue;
        //        }
        //    }
        //
        //    reader.close();
        //} catch (FileNotFoundException fnfe) {
        //    // ignore
        //} catch (IOException ioe) {
        //    log.debug("tmb file error!", ioe);
        //}

        //boolean 岁 = NUMBER_PATTERN.matcher("11岁").find();
        //boolean 岁2 = POSITIVE_NUMBER_PATTERN.matcher("11").find();
        //boolean 岁3 = POSITIVE_NUMBER_PATTERN2.matcher("11水").find();
        //boolean b1 = POSITIVE_NUMBER_PATTERN.matcher("-33").find();
        //boolean b21 = POSITIVE_NUMBER_PATTERN.matcher("34岁").find();
        //int i2 = Integer.parseInt("33");
        //String a = "33";
        //String b = "34";
        //
        //String uuid = UUID.randomUUID().toString();
        //uuid = StringUtils.replace(uuid, "-", "");
        //if (StringUtils.isNotEmpty("123")) {
        //    uuid = "123" + "-" + uuid;
        //}
        //
        //Map <String, String> map = new HashMap <>();
        //map.put("123", "1233");
        //map.remove("123");
        String json = "{\n" +
                "\t\"response\" : {\n" +
                "\t\t\"code\" : 1000,\n" +
                "\t\t\"PathologyInfoList\" : {\n" +
                "\t\t\t\"PathologyInfo\" : [ {\n" +
                "\t\t\t\t\"idNumber\" : 340826194310180013,\n" +
                "\t\t\t\t\"pathologyReportId\" : \"2019-26054\",\n" +
                "\t\t\t\t\"pathologyReportType\" : \"cg\",\n" +
                "\t\t\t\t\"pathologyReportNum\" : 1,\n" +
                "\t\t\t\t\"pathologyReportTime\" : \"2019-06-10 15:47:12\",\n" +
                "\t\t\t\t\"pathologyReportDoctor\" : \"常佳\",\n" +
                "\t\t\t\t\"pathologyReportChecker\" : \"李媛\",\n" +
                "\t\t\t\t\"pathologyReportResult\" : \"（右肺，穿刺活检）少许低分化非小细胞癌，需行免疫组化进一步诊断，请来病理科办理付费手续。\\n\\n\\n补充报告：\\n（右肺，穿刺活检）少许低分化非小细胞癌，结合免疫组化，符合肺腺癌。\\n免疫组化（HI19-13768）\\nTTF-1（+），AE1/AE3（+），ALK-Ventana（-），NapsinA（+），P40（-），Ki-67（+）\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"idNumber\" : 340826194310180013,\n" +
                "\t\t\t\t\"pathologyReportId\" : \"BF19-02293\",\n" +
                "\t\t\t\t\"pathologyReportType\" : \"cg\",\n" +
                "\t\t\t\t\"pathologyReportNum\" : 1,\n" +
                "\t\t\t\t\"pathologyReportTime\" : \"2019-04-29 11:23:29\",\n" +
                "\t\t\t\t\"pathologyReportDoctor\" : \"郭天威\",\n" +
                "\t\t\t\t\"pathologyReportChecker\" : \"李媛\",\n" +
                "\t\t\t\t\"pathologyReportResult\" : \"（右肺中叶外侧段支气管，活检）肺组织中见极个别异型细胞，由于可供诊断组织太少，建议重取活检。\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"idNumber\" : 340826194310180013,\n" +
                "\t\t\t\t\"pathologyReportId\" : \"E2019-00608\",\n" +
                "\t\t\t\t\"pathologyReportType\" : \"cg\",\n" +
                "\t\t\t\t\"pathologyReportNum\" : 1,\n" +
                "\t\t\t\t\"pathologyReportTime\" : \"2019-04-26 16:38:33\",\n" +
                "\t\t\t\t\"pathologyReportDoctor\" : \"王彦丽\",\n" +
                "\t\t\t\t\"pathologyReportChecker\" : \"陈颖\",\n" +
                "\t\t\t\t\"pathologyReportResult\" : \"（右肺中叶外侧段支气管）见支气管上皮细胞，少量异形细胞，癌不能除外。\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"idNumber\" : 340826194310180013,\n" +
                "\t\t\t\t\"pathologyReportId\" : \"IM2019-06232\",\n" +
                "\t\t\t\t\"pathologyReportType\" : \"cg\",\n" +
                "\t\t\t\t\"pathologyReportNum\" : 1,\n" +
                "\t\t\t\t\"pathologyReportTime\" : \"2019-05-22 14:10:31\",\n" +
                "\t\t\t\t\"pathologyReportDoctor\" : \"张皓\",\n" +
                "\t\t\t\t\"pathologyReportChecker\" : \"王龙富\",\n" +
                "\t\t\t\t\"pathologyReportResult\" : \"见癌细胞，非小细胞癌。\"\n" +
                "\t\t\t} ]\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        //Map<String,Object> response = new JsonParser().parse(json);
        //Map<String,Object> response1 = (Map<String,Object>)response.get("response");
        //ArrayList <Map<String,Object>> pathologyInfoList = (ArrayList <Map <String, Object>>) ((Map<String,Object>) (response1.get("PathologyInfoList"))).get("PathologyInfo");


        //Date date = DateUtils.parseDateStrictly("2018-04-04 00:00:00", "yyyy-MM-dd HH:mm:ss");
        //System.out.println();

        /**
         * 计算整数除法 相除 百分比 百分号 保留小数点 精度 2种方法
         *
         */
        //String s = MyMathUtils.calcDivisionResultToPercent("100", "1100", 2);
        //int a=100;
        //int b=1100;
        //NumberFormat percent = NumberFormat.getPercentInstance();
        //percent.setMaximumFractionDigits(2);
        ////除法结果保留4位小数，
        //double per = new BigDecimal((float)a/b).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
        ////格式化为百分比字符串（自带百分号）
        //String Ratio = percent.format(per);
        //
        //BigDecimal bi1 = new BigDecimal(100);
        //BigDecimal bi2 = new BigDecimal(1100);
        //double v= bi1.divide(bi2, 4, RoundingMode.HALF_UP).doubleValue();
        //String Ratio1 = percent.format(v);
        //String s = String.format("%.2f", (((float) 100) / 1100) * 100) + "%";

        /**
         * easyExcel 可以把列头读出来
         * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
         * https://alibaba-easyexcel.github.io/quickstart/read.html#%E5%90%8C%E6%AD%A5%E7%9A%84%E8%BF%94%E5%9B%9E
         */
        String fileName = "/2019-10-11 14_59_54.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        // 可以读出第一行列头
        //List <Object> objects = EasyExcel.read(fileName).sheet().headRowNumber(0).doReadSync();
        //// 不读出列头 headRowNumber 默认为1
        //List <Object> objects1 = EasyExcel.read(fileName).sheet().doReadSync();

        /**
         * 判断程序是否运行在debug模式
         */
        boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
                getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

        /**
         * 递归拷贝文件和文件夹并加上过滤器
         *
         */
        //File source = new File("/abc");
        //File dest = new File("/abcd");
        //try {
        //    //FileUtils.copyDirectory(source, dest);
        //    FileUtils.copyDirectory(source, dest, new FileFilter() {
        //        @Override
        //        public boolean accept(File file) {
        //            // 不需要拷贝的文件
        //            if (StringUtils.containsIgnoreCase(file.getParentFile().getName(), "SampleSheet.csv") || StringUtils.containsIgnoreCase(file.getParentFile().getName(), "DispatchComplete.txt")) {
        //                return true;
        //            }
        //            if (file.isDirectory()) {
        //                return true;
        //            }
        //            if (StringUtils.endsWithIgnoreCase(file.getName(), "fastq.gz")) {
        //                return true;
        //            }
        //            if (StringUtils.equalsIgnoreCase(file.getParentFile().getName(), "abc")) {
        //                return true;
        //            }
        //            return false;
        //        }
        //    });
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        /**
         * 该Utils可以对html文本进行转义和反转义 例如: & > < ? 空格 等字符
         *
         */
        //String fooasd = "&";
        //String replace = StringUtils.replace(fooasd, "&", "&amp;");
        //String s4 = StringEscapeUtils.escapeHtml4(fooasd);
        //String s3 = StringEscapeUtils.unescapeHtml4(fooasd);
        //String s23 = StringEscapeUtils.unescapeHtml3(fooasd);

        /**
         * 替换正则的特殊字符成能使用的正则表达式
         *
         */
        //String s = "NR-21:(-);\nBAT-26:(-);\nBAT-25:(-);\nNR-24:(-);\nMONO-27:(-);\n";
        //String asdf = "BAT-26:(-);";
        //boolean b2 = Pattern.compile(asdf).matcher(s).find();
        //String s1 = RegexUtils.makeQueryStringToRegExp(asdf);
        //boolean b22 = Pattern.compile(s1).matcher(s).find();


        /**
         * 两个日期相差的天数
         */
        //Date date1 = new Date();
        //Date date2 = format.parse("2019-06-11 00:00:00");
        //int days = Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000*3600*24)));


        /**
         * 排序
         */
        //List <String> stageList = new ArrayList <>();
        //stageList.add("third");
        //stageList.add("seventh");
        //stageList.add("fourth");
        //stageList.add("first");
        //stageList.sort(Comparator.comparing(SOPStage::valueOf));
        //
        //List <Integer> lists = new ArrayList <>();
        //lists.add(7);
        //lists.add(999);
        //lists.add(2);
        //lists.add(4);
        //lists.add(5);
        //lists.add(6);
        //
        //lists.sort(Comparator.comparing(Integer::valueOf));
        //System.out.println(lists);

        /**
         * 裁剪字符串
         */
        //String _otherPathologicalNumber = StringUtils.substringBetween("1、2019-11275   数目：HE*5 US*20 2、   数目：    3、  数目：   4、   数目：", "1、", "数目").trim();
        //
        //int i = (int) Math.ceil((3 / 2.0));
        //double ceil = Math.ceil(1.2);
        //
        //String 数目 = StringUtils.substringBetween("1、1603555  数目：HE*1，US*20   2、1805577   数目：HE*5，US*20    3、  数目：   4、   数目：", "1、", "数目").trim();
        //String a = "a b";
        //String b = "我 去";
        //int msWordsCount = getMSWordsCount(a);
        //int msWordsCount1 = getMSWordsCount(b);
        //int length = a.length();
        //int length1 = b.length();
        //String str="xxxxxxxx进枯时进田团团圆圆大厅大规模";
        //int count=getMSWordsCount(str);
        //System.out.println(123);

        /**
         * 解析xml
         *
         */
        //List <File> sopBatchDirList = Arrays.asList(new File("/data/nfs/gcsas-sop").listFiles(new FileFilter() {
        //    @Override
        //    public boolean accept(File file) {
        //        if (file.isDirectory()) {
        //            return true;
        //        }
        //        return false;
        //    }
        //}));
        //
        //for (File projectFile : sopBatchDirList) {
        //    if (!projectFile.exists()) {
        //        log.warn("The sop project file does not exist! project file name is {}", "");
        //        continue;
        //    }
        //    String _project = projectFile.getName();
        //    System.out.println(123);
        //}
        //File projectFile = new File("/data/nfs/gcsas-sop/ngs");
        //List <File> xmlList = Arrays.asList(projectFile.listFiles(new FileFilter() {
        //    @Override
        //    public boolean accept(File file) {
        //        if (file.isFile()) {
        //            return true;
        //        }
        //        return false;
        //    }
        //}));
        //
        //// 按照目录名成排序
        //Collections.sort(xmlList, new Comparator<File>() {
        //    @Override
        //    public int compare(File o1, File o2) {
        //        return o1.getName().compareTo(o2.getName());
        //    }
        //});
        //for (File xmlFile : xmlList) {
        //    String absolutePath = xmlFile.getAbsolutePath();
        //    String name = xmlFile.getName();
        //    List <String> strings = Arrays.asList(xmlFile.getName().replace(".xml", "").split("_"));
        //    System.out.println(123);
        //
        //}

        /**
         * 切割字符串
         */
        //String s = "1. A00001 数目 ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";
        //String s1 = StringUtils.substringBetween(s, "1.", "数目").trim();
        //System.out.println(s1);

        /**
         * 设定时间格式
         */
        //Date date = DateUtils.parseDateStrictly("2018-04-04 00:00:00", "yyyy-MM-dd HH:mm:ss");
        //DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String s = "2019-03-01 12:12:12";
        //String s1 = s.toString();
        //Date parse = format.parse(s1);
        //System.out.println(parse);

        /**
         * 正则
         */
        //Pattern p=Pattern.compile("测序|FISH|ARMS");
        //Matcher m = p.matcher("ARMS=123");
        //boolean b = m.lookingAt();
        //System.out.println(Pattern.matches("测序", "测序=123"));
        //System.out.println(Pattern.matches("\\d+", "2223"));

        /**
         *  csv文件映射对象 CSVMutationSite
         *  https://blog.csdn.net/wangjun5159/article/details/51655806
         */
        //try {
        //    CSVReader reader = new CSVReader(new FileReader("/gcbi/storage/gcsas/gcsas-ranshi/target/MP2018" +
        //            "-00972_report.csv"));
        //    //CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("/gcbi/storage/gcsas/gcsas" +
        //    //        "-ranshi/target/MP2018-00972_report.csv"),"utf-8"),',');
        //    HeaderColumnNameMappingStrategy<CSVMutationSite> mapper = new HeaderColumnNameMappingStrategy<>();
        //    mapper.setType(CSVMutationSite.class);
        //    CsvToBean<CSVMutationSite> csvToBean = new CsvToBean<>();
        //    csvToBean.setMappingStrategy(mapper);
        //    csvToBean.setCsvReader(reader);
        //    List<CSVMutationSite> list = csvToBean.parse();
        //    for(CSVMutationSite e : list) {
        //        System.out.println(e);
        //    }
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //}

        /**
         * 日期设定
         */
        // 30
        //Date date = DateUtils.parseDateStrictly("2018-04-04 00:00:00", "yyyy-MM-dd HH:mm:ss");
        //DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //String endTime = simpleDateFormat.format(new Date());
        //Calendar c1 = Calendar.getInstance();
        //c1.add(Calendar.DATE, -31);
        //String startTime = simpleDateFormat.format(c1.getTime());
        //Date startDate = null;
        //Date endDate = null;
        //try {
        //    startDate = simpleDateFormat.parse(startTime + " 00:00:00");
        //    endDate = simpleDateFormat.parse(endTime + " 23:59:59");
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        //int diff = differentDaysByMillisecond(startDate, endDate);
        //
        //// 1
        //Date startTime1 = new Date();
        //startTime1 = DateUtils.setHours(startTime1, 0);
        //startTime1 = DateUtils.setMinutes(startTime1, 0);
        //startTime1 = DateUtils.setSeconds(startTime1, 0);
        //
        //Date endTime2 = new Date();
        //endTime2 = DateUtils.setHours(endTime2, 23);
        //endTime2 = DateUtils.setMinutes(endTime2, 59);
        //endTime2 = DateUtils.setSeconds(endTime2, 59);
        //int diff2 = differentDaysByMillisecond(startTime1, endTime2);
        //System.out.println(123);

        /**
         * 读取csv文件数据
         */
        //Map <String, String> map1 = readDataOfMSIFromCsvPath("C:\\gcbi\\storage\\gcsas\\gcsas-ranshi\\target\\MP2019-06112_MSI.csv");
        //File src = new File("C:\\gcbi\\storage\\mas\\mas-report\\5bfb9165047e5ca2eed48215");
        //String parent = src.getParent();
        //File[] allFile = src.listFiles();
        //log.debug("allFile arrays : {}", Arrays.toString(allFile));
        //for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
        //    if (!allFile[currentFile].isDirectory()) {
        //        String srcName = allFile[currentFile].getPath().toString();
        //        System.out.println(123);
        //    }
        //}
        //File file = new File("C:\\gcbi\\storage\\gcsas\\gcsas-sample\\TEST\\SampleSheet.csv");
        //Map <String, String> map = parseSampleSheet(file, "Description");
        //String s = map.get("MP2019-02183");
        //List <String> testTagList = Arrays.asList("asdf", "ffff");
        //String[] testTagArr = testTagList.toArray(new String[testTagList.size()]);
        //String testTag = StringUtils.join(testTagArr, ",");
        //Collections.swap(testTagList, 0, 1);
        //testTagArr = testTagList.toArray(new String[testTagList.size()]);
        //testTag  = StringUtils.join(testTagArr, ",");

        /**
         * 设定日期
         */
        // 设定时间为2013年
        //Date date3 = DateUtils.setYears(date6, 2013);
        //DateTimeLiteralExpression.DateTime dateTime = new DateTime(date3);
        //// 设定为当前年的3月份
        //DateTimeLiteralExpression.DateTime dateTime1 = dateTime.monthOfYear().setCopy(3);
        //// 设定为每月的开始和结束
        //DateTimeLiteralExpression.DateTime timeAtStartOfMonth = dateTime.monthOfYear().setCopy(3).dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        //DateTimeLiteralExpression.DateTime timeAtEndOfMonth = dateTime.monthOfYear().setCopy(3).dayOfMonth().withMaximumValue().plusDays(1).withTimeAtStartOfDay();
        //DateTimeLiteralExpression.DateTime timeAtStartOfMonth1 = dateTime.monthOfYear().setCopy(5).dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        //DateTime timeAtEndOfMonth1 = dateTime.monthOfYear().setCopy(5).dayOfMonth().withMaximumValue().plusDays(1).withTimeAtStartOfDay();
        //Date date1 = dateTime.withDayOfMonth(1).withTimeAtStartOfDay().toDate();
        //Date date2 = dateTime.minusDays(30).toDate();
        //int i = date1.compareTo(date2);
        //boolean after = date1.after(date2);
        //Date startDate1 = dateTime.withDayOfWeek(1).withTimeAtStartOfDay().toDate();
        //Date endDate1 = dateTime.withDayOfWeek(1).plusWeeks(1).withTimeAtStartOfDay().toDate();
        //SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        //String formatDate = simpleDateFormat2.format(startDate1);
        //String formatDate1 = simpleDateFormat2.format(endDate1);
        //try {
        //    Object parse = simpleDateFormat2.parse(formatDate);
        //    Object parse2 = simpleDateFormat2.parse(formatDate1);
        //    System.out.println(123);
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}

        /**
         * 测试在csv文件中写入修改数据
         */
        //Map<String, String> dispatcherMap = new HashMap<>();
        //dispatcherMap.put("AE", "/data/nfs/dispach-test1");
        //dispatcherMap.put("79MP", "/data/nfs/dispach-test2");
        //dispatcherMap.put("SH", "/data/nfs/dispach-test3");
        //for (Map.Entry<String,String> entry : dispatcherMap.entrySet()) {
        //    String key = entry.getKey();
        //    String value = entry.getValue();
        //    System.out.println("Key = " + key +
        //            ", Value = " + value);
        //}
        //
        //try {
        //    CSVReader reader = new CSVReader(new FileReader("D:/old.csv"));
        //    CSVWriter writer = new CSVWriter(new FileWriter("D:/new.csv"));
        //    String [] nextLine;
        //    Boolean canEdit = false;
        //    Integer sampleNameIndex = null;
        //    while ((nextLine = reader.readNext()) != null) {
        //        List<String> lineAsList = new ArrayList<String>(Arrays.asList(nextLine));
        //        // Add stuff using linesAsList.add(index, newValue) as many times as you need.
        //        // 找到Sample_Name在第几列, 第二列 sampleNameIndex = 1;
        //        if (StringUtils.containsIgnoreCase(lineAsList.get(0), "Sample_ID")) {
        //            for (int i = 0; i < lineAsList.size(); i++) {
        //                if (StringUtils.containsIgnoreCase(lineAsList.get(i), "Sample_Name")) {
        //                    sampleNameIndex = i;
        //                    break;
        //                }
        //            }
        //            canEdit = true;
        //        }
        //        if (canEdit) {
        //            // sampleNameIndex example: 1
        //            if (!StringUtils.containsIgnoreCase(lineAsList.get(sampleNameIndex),
        //                    "79MP")) {
        //                continue;
        //            }
        //        }
        //        String[] lineAsArr = lineAsList.toArray(new String[lineAsList.size()]);
        //        writer.writeNext(lineAsArr);
        //    }
        //    writer.close();
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //
        //try {
        //    File csv = new File("D:/writers.csv"); // CSV数据文件
        //
        //    csv.createNewFile();
        //    BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
        //    // 添加新的数据行
        //    bw.write("李四" + "," + "1988" + "," + "1992");
        //    bw.newLine();
        //    bw.close();
        //
        //} catch (FileNotFoundException e) {
        //    // File对象的创建过程中的异常捕获
        //    e.printStackTrace();
        //} catch (IOException e) {
        //    // BufferedWriter在关闭对象捕捉异常
        //    e.printStackTrace();
        //}


    }

/* private methods ----------------------------------------------------- */

/* getters/setters ----------------------------------------------------- */
enum OperationType {
    syncMolecularPathology("同步分子病理数据"),
    referInfo("查询信息"),
    editSopForm("编辑sop表单或表格"),
    updateSopForm("更新sop表单或表格"),
    updateInfo("更新信息"),
    createAnalyze("创建分析"),
    continueAnalyze("继续分析"),
    reAnalyze("重新分析"),
    analyzed("分析完成"),
    verifying("开始审核"),
    verified("审核完成"),
    submitCheck("提交复核"),
    checking("开始复核"),
    checked("复核完成"),
    submitReview("提交重新验证"),
    editMutationSite("更改位点数据"),
    prepareReport("准备报告"),
    editReport("编辑报告"),
    sendReport("发送报告"),
    archive("归档"),
    terminate("分析终止"),
    withdraw("撤回");

    String title;

    private OperationType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }


}

    public static int getMSWordsCount(String context) {
        int words_count = 0;
        //中文单词
        String cn_words = context.replaceAll("[^(\\u4e00-\\u9fa5，。《》？；’‘：“”【】、）（……￥！·)]", "");
        int cn_words_count = cn_words.length();
        //非中文单词
        String non_cn_words = context.replaceAll("[^(a-zA-Z0-9`\\-=\';.,/~!@#$%^&*()_+|}{\":><?\\[\\])]", " ");
        int non_cn_words_count = 0;
        String[] ss = non_cn_words.split(" ");
        for (String s : ss) {
            if (s.trim().length() != 0) non_cn_words_count++;
        }
//中文和非中文单词合计
        words_count = cn_words_count + non_cn_words_count;
//        ToolLog.d(ConstString.TAG, "汉字：" + cn_words_count + "\n\t字符：" + non_cn_words_count);
        return words_count;
    }

    /**
     * 解析sampleSheet文档，指定表头字段拿到对应分子病理号的数据
     *
     * @param sampleSheetFile
     * @param header
     * @return
     */
    public static Map <String, String> parseSampleSheet(File sampleSheetFile, String header) {
        Assert.notNull(sampleSheetFile);
        Assert.isTrue(sampleSheetFile.exists());
        try {
            CsvReader reader = new CsvReader(sampleSheetFile.getAbsolutePath());
            boolean start = false;
            Map <String, String> sampleInfoMap = new HashMap <>();
            List <String> filedList = new ArrayList <>();
            while (reader.readRecord()) {
                if ("[Data]".equals(reader.get(0))) {
                    start = true;
                    reader.readRecord();
                    String rawRecord = reader.getRawRecord();
                    filedList.addAll(Arrays.asList(rawRecord.split(",")));
                    continue;
                }
                if (!start) {
                    continue;
                }
                String sampleId = reader.get(0);

                // AE打头的样本编号例如： AE1700555FFP。
                String headerValue = null;
                for (int i = 0; i < filedList.size(); i++) {
                    if (StringUtils.containsIgnoreCase(filedList.get(i), header)) {
                        headerValue = reader.get(i);
                        break;
                    }
                }
                if (StringUtils.isEmpty(sampleId)) {
                    break;
                }
                // 以MP或者mp开头的sampleId为分子病理号
//				if (sampleId.startsWith("MP") || sampleId.startsWith("mp")) {
//					sampleInfoMap.put(sampleId, sampleName);
//				}
                if (StringUtils.isEmpty(headerValue)) {
                    sampleInfoMap.put(sampleId, "");
                } else {
                    sampleInfoMap.put(sampleId, headerValue);
                }
            }

            reader.close();
            return sampleInfoMap;
        } catch (FileNotFoundException fnfe) {
            // ignore
        } catch (IOException ioe) {
            log.debug("Parse sampleSheet file error!", ioe);
        }
        return null;
    }

    public static Map <String, String> readDataOfMSIFromCsvPath(String path) {
        Map <String, String> msiMap = new HashMap <>();
        try {
            CsvReader reader = new CsvReader(path);
            reader.setDelimiter('\t');
            reader.readHeaders();
            String[] headers = reader.getHeaders();
            while (reader.readRecord()) {
                String s = reader.get(0);
                for (int i = 0; i < headers.length; i++) {
                    if (StringUtils.equalsIgnoreCase(headers[i], "coveraged.loci.num")) {
                        msiMap.put("coveraged.loci.num", reader.get(i));
                    }
                    if (StringUtils.equalsIgnoreCase(headers[i], "msi.loci.num")) {
                        msiMap.put("msi.loci.num", reader.get(i));
                    }
                    if (StringUtils.equalsIgnoreCase(headers[i], "NGS")) {
                        msiMap.put("NGS", reader.get(i));
                    }
                }
                break;
            }
            reader.close();
        } catch (FileNotFoundException fnfe) {
            // ignore
        } catch (IOException ioe) {
            log.debug("msi file error!", ioe);
        }
        return msiMap;
    }

    public static void readDataOfMSIFromCsvPath() {
    }

    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime() + 2000) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * easyExcel 写
     * web中的写在testController中
     */

    /**
     * 不创建对象的写
     * https://alibaba-easyexcel.github.io/quickstart/write.html#%E4%B8%8D%E5%88%9B%E5%BB%BA%E5%AF%B9%E8%B1%A1%E7%9A%84%E5%86%99
     */
    public static void noModleWrite() {
        // 写法1
        String fileName = "C:\\Users\\kavanLi\\Desktop\\noModleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
        EasyExcel.write(fileName).head(head()).sheet("模板A").doWrite(dataList());
    }

    /**
     * 使用table去写入
     * https://alibaba-easyexcel.github.io/quickstart/write.html#%E4%BD%BF%E7%94%A8table%E5%8E%BB%E5%86%99%E5%85%A5
     */
    public static void tableWrite() {
        String fileName = "C:\\Users\\kavanLi\\Desktop\\tableWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案例
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(fileName).head(head()).build();
        // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").needHead(Boolean.FALSE).build();
        // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
        WriteTable writeTable0 = EasyExcel.writerTable(0).head(head()).needHead(Boolean.TRUE).build();
        WriteTable writeTable1 = EasyExcel.writerTable(1).head(head1()).needHead(Boolean.TRUE).build();
        // 第一次写入会创建头
        excelWriter.write(dataList(), writeSheet, writeTable0);
        // 第二次写如也会创建头，然后在第一次的后面写入数据
        excelWriter.write(dataList(), writeSheet, writeTable1);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    private static List <List <String>> head() {
        List <List <String>> list = new ArrayList <List <String>>();
        List <String> head0 = new ArrayList <String>();
        head0.add("字符串" + System.currentTimeMillis());
        List <String> head1 = new ArrayList <String>();
        head1.add("数字" + System.currentTimeMillis());
        List <String> head2 = new ArrayList <String>();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private static List <List <String>> head1() {
        List <List <String>> list = new ArrayList <List <String>>();
        List <String> head0 = new ArrayList <String>();
        head0.add("字符串A" + System.currentTimeMillis());
        List <String> head1 = new ArrayList <String>();
        head1.add("数字A" + System.currentTimeMillis());
        List <String> head2 = new ArrayList <String>();
        head2.add("日期A" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private static List <List <Object>> dataList() {
        List <List <Object>> list = new ArrayList <List <Object>>();
        for (int i = 0; i < 10; i++) {
            List <Object> data = new ArrayList <Object>();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(0.56);
            list.add(data);
        }
        return list;
    }

}
