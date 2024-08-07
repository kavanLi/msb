package com.example.demo.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.OperationLogAnnotation;
import com.mashibing.internal.common.annotation.RateLimit;
import com.mashibing.internal.common.domain.User;
import com.mashibing.internal.common.domain.model.CommonResponse;
import com.mashibing.internal.common.utils.DateOrTimeUtils;
import com.mashibing.internal.common.config.easyExcel.ConverterData;
import com.mashibing.internal.common.config.easyExcel.DemoData;
import io.swagger.annotations.ApiOperation;
import org.joda.time.LocalDateTime;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kavanLi
 * @create: 2020-01-06 14:07
 * To change this template use File | Settings | File and Code Templates.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping(value = "")
    @RateLimit(permitsPerSecond = 1, interval = 3) // 限流 每3秒限制1个请求
    public Map <String, String> test() {
        System.out.println(123123123);
        Map <String, String> errors = new HashMap <>();
        errors.put("123", "1231");
        return errors;
    }


    // 使用 @RequestBody 注解， 传参正常
    // 无法通过application/x-www-form-urlencoded传参
    //public User user(User req) // 不使用 @RequestBody 注解，正好相反，application/x-www-form-urlencoded 传参正常
    // 无法通过 application/json 传参
    @PostMapping(value = "/user11")
    public User user1(User req) {
        User user = new User();
        user.setGmt_create(new Date());
        return req;
    }

    /**
     * 测试 配置
     * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
     * spring.jackson.time-zone=GMT+8
     *
     * spring.jackson.date-format 用于指定日期的格式，例如 "yyyy-MM-dd HH:mm:ss"，这样在序列化和反序列化 JSON 数据时，日期字段将会按照指定的格式进行处理。如果不设置，默认情况下 Jackson 会使用 ISO 8601 格式（例如 "yyyy-MM-dd'T'HH:mm:ss.SSSZ"）来处理日期。
     * spring.jackson.time-zone 用于指定时区，例如 "GMT+8"，这样在序列化和反序列化 JSON 数据时，日期字段将会以指定的时区进行处理。如果不设置，默认情况下 Jackson 会使用系统默认的时区。
     * 通过在 Spring Boot 的配置文件中设置这两个属性，可以全局地配置整个应用程序中 Jackson 库对日期和时间的处理方式，确保在 JSON 数据的序列化和反序列化过程中符合预期的格式和时区要求。
     *
     * @return
     */
    @GetMapping(value = "/user")
    public User test1() {
        User user = new User();
        user.setGmt_create(new Date());
        /**
         * localDateTime转换报错
         * Joda date/time type `org.joda.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-joda" to enable handling (through reference chain: com.example.demo.domain.User["gmt_localDateTimeCreate"])
         *
         * 需要增加依赖
         *
         * <dependency>
         *     <groupId>com.fasterxml.jackson.datatype</groupId>
         *     <artifactId>jackson-datatype-joda</artifactId>
         *     <version>2.13.1</version>
         * </dependency>
         *
         * 似乎配置中的 spring.jackson.date-format 和 spring.jackson.time-zone 并没有生效，因为 LocalDateTime 对象的输出仍然采用了默认的格式。这可能是因为 LocalDateTime 不受这些配置项的影响，因为它不是 Jackson 默认支持的日期类型。
         *
         * 通过在 LocalDateTime 字段上添加 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 注解，你可以指定它的输出格式。这样配置后，返回的 JSON 中 gmt_localDateTimeCreate 字段应该会按照指定的格式进行格式化了。
         */
        user.setGmt_localDateTimeCreate(LocalDateTime.now());
        return user;
    }

    // 使用 @RequestBody 注解， 传参正常
    // 无法通过application/x-www-form-urlencoded传参
    //public User user(User req) // 不使用 @RequestBody 注解，正好相反，application/x-www-form-urlencoded 传参正常
    // 无法通过 application/json 传参
    @PostMapping(value = "/user1")
    public User user(@RequestBody User req) {
        User user = new User();
        user.setGmt_create(new Date());
        return user;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/world")
    public String testWorld(@RequestBody String reqStr) {
        System.out.println(reqStr);
        return "testWorld";
    }

    @GetMapping(value = "/secure")
    public Map <String, String> secure() {
        System.out.println(123123123);
        Map <String, String> errors = new HashMap <>();
        errors.put("123", "secure");
        return errors;
    }


    @GetMapping(value = "/foobar")
    @OperationLogAnnotation
    public Map <String, String> test1323() {
        System.out.println(123123123);
        Map <String, String> errors = new HashMap <>();
        errors.put("123", "foobar");
        return errors;
    }


    /**
     * {
     * "$request": "Simulated HttpServletRequest Data",
     * "manageModel": "SimulatedManageModel",
     * "acctOrgType": "SimulatedAcctOrgType",
     * "accountNo": "SimulatedAccountNo",
     * "accountName": "SimulatedAccountName",
     * "bankName": "SimulatedBankName",
     * "bankCode": "SimulatedBankCode",
     * "bankProvince": "SimulatedBankProvince",
     * "bankCity": "SimulatedBankCity",
     * "paymentMinAmount": "SimulatedPaymentMinAmount",
     * "paymentMaxAmount": "SimulatedPaymentMaxAmount",
     * "createUser": "SimulatedCreateUser",
     * "updateUser": "SimulatedUpdateUser"
     * }
     *
     * JSON.stringify() ->
     *
     * '{"$request":"Simulated HttpServletRequest Data","manageModel":"SimulatedManageModel","acctOrgType":"SimulatedAcctOrgType","accountNo":"SimulatedAccountNo","accountName":"SimulatedAccountName","bankName":"SimulatedBankName","bankCode":"SimulatedBankCode","bankProvince":"SimulatedBankProvince","bankCity":"SimulatedBankCity","paymentMinAmount":"SimulatedPaymentMinAmount","paymentMaxAmount":"SimulatedPaymentMaxAmount","createUser":"SimulatedCreateUser","updateUser":"SimulatedUpdateUser"}'
     *
     * EncodeUri() ->
     *
     * '%7B%22$request%22:%22Simulated%20HttpServletRequest%20Data%22,%22manageModel%22:%22SimulatedManageModel%22,%22acctOrgType%22:%22SimulatedAcctOrgType%22,%22accountNo%22:%22SimulatedAccountNo%22,%22accountName%22:%22SimulatedAccountName%22,%22bankName%22:%22SimulatedBankName%22,%22bankCode%22:%22SimulatedBankCode%22,%22bankProvince%22:%22SimulatedBankProvince%22,%22bankCity%22:%22SimulatedBankCity%22,%22paymentMinAmount%22:%22SimulatedPaymentMinAmount%22,%22paymentMaxAmount%22:%22SimulatedPaymentMaxAmount%22,%22createUser%22:%22SimulatedCreateUser%22,%22updateUser%22:%22SimulatedUpdateUser%22%7D'
     *
     * 通过postman，Json字符串作为请求参数时，需要把编码后reqJson进行传参
     *
     * *URL 编码（Percent-Encoding）**是一种将特殊字符以及非ASCII字符转换成 % 后跟两位十六进制数的形式的编码方式。这是因为在URL中，某些字符可能会被解释为控制字符或特殊字符，为了避免歧义和错误解释，需要对这些字符进行编码。
     * 总体来说，URL 编码和解码是一种通用的机制，确保在网络传输中的数据完整性和可靠性。在使用 RESTful API 时，尤其是在 URL 参数中传递数据时，进行 URL 编码是一种良好的实践，可以避免因为特殊字符引起的问题。
     *
     * @return
     */
    @RequestMapping(value = "/testJsonObject", method = {RequestMethod.POST, RequestMethod.GET})
    @OperationLogAnnotation
    public ResponseEntity <JSONObject> testJsonObject(@RequestBody User user) {
        JSONObject result = new JSONObject();
        result.put("code", "fail");
        result.put("msg", "exception.getMessage()");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/testRequestBodyStr", method = {RequestMethod.POST, RequestMethod.GET})
    @OperationLogAnnotation
    @ApiOperation(value = "获取商户代号")
    public ResponseEntity <?> encodeJsonStr(@RequestBody String request) {
        //JSONObject jsonObject = new JSONObject(request);
        //JSONObject result = new JSONObject();

        User user = JSON.parseObject(request, User.class);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(request);
        String str = String.valueOf(jsonObject.getJSONObject("INFO").get("ORGID"));
        //result.put("code", "fail");
        //result.put("msg", "exception.getMessage()");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/test2")
    public ResponseEntity <Map> test2() {
        Map result = new HashMap <>();
        result.put("code", "fail");
        result.put("msg", "ResponseEntity test");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test3")
    public CommonResponse <?> test3() {
        CommonResponse <Object> objectCommonResponse = new CommonResponse <>();
        objectCommonResponse.setAppid("123");
        objectCommonResponse.setReqsn("321");
        User user = new User();
        user.setAge(111);
        user.setUid(123123L);
        user.setEmail("testesdfsdf");
        objectCommonResponse.setData(Arrays.asList(user));
        return objectCommonResponse;
    }


    /**
     * easyExcel 写
     * 读和写demo在Demo.java中
     */

    /**
     * web中的写
     *
     * https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java
     *
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link }
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        //// 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        //response.setContentType("application/vnd.ms-excel");
        //response.setCharacterEncoding("utf-8");
        //// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        //String fileName = URLEncoder.encode("测试", "UTF-8");
        //response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        //EasyExcel.write(response.getOutputStream()).head(head()).sheet("模板").doWrite(dataList());
        //EasyExcel.write(response.getOutputStream()).head(head1()).sheet("模板1").doWrite(dataList());

        /**
         * 使用table去写入
         */

        String fileName = "1234_";

        //// 开始把数据写入excel 并让web下载
        // application/vnd.ms-excel不支持压缩，通常用于旧版本的 Microsoft Excel 文件 (.xls)。兼容所有版本的Microsoft Excel
        //response.setContentType("application/vnd.ms-excel");

        // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet 支持压缩，通常用于新版本的 Microsoft Excel 文件 (.xlsx)
        // 。不兼容旧版本的 Microsoft Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        //response.setHeader("Content-disposition",
        //        "attachment;filename=" + fileName + DateFormatUtils.format(new Date(),
        //                "_yyyy-MM-dd HH:mm:ss") + ".xlsx");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + DateOrTimeUtils.dateTimeFormatter.format(java.time.LocalDateTime.now()) + ".xlsx");
        /**
         * 使用table去写入
         */
        // 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案例
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").needHead(Boolean.FALSE).build();
        // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
        WriteTable writeTable0 = EasyExcel.writerTable(0).head(head()).needHead(Boolean.TRUE).build();
        WriteTable writeTable1 = EasyExcel.writerTable(1).head(head1()).needHead(Boolean.TRUE).build();
        WriteTable writeTable2 = EasyExcel.writerTable(2).head(head2()).needHead(Boolean.TRUE).build();
        WriteTable writeTable3 = EasyExcel.writerTable(3).head(head1()).needHead(Boolean.TRUE).build();
        WriteTable writeTable4 = EasyExcel.writerTable(4).head(head2()).needHead(Boolean.TRUE).build();

        // 第一次写入会创建头
        excelWriter.write(dataList(), writeSheet, writeTable0);
        // 第二次写如也会创建头，然后在第一次的后面写入数据
        excelWriter.write(dataList1(), writeSheet, writeTable1);
        excelWriter.write(dataList2(), writeSheet, writeTable2);
        excelWriter.write(dataList1(), writeSheet, writeTable3);
        excelWriter.write(dataList(), writeSheet, writeTable4);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    @GetMapping("simpleDownload")
    public void simpleDownload(HttpServletResponse response) throws IOException {
        String fileName = "1234_";

        // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet 支持压缩，通常用于新版本的 Microsoft Excel 文件 (.xlsx)
        // 。不兼容旧版本的 Microsoft Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + DateOrTimeUtils.dateTimeFormatter.format(java.time.LocalDateTime.now()) + ".xlsx");

        // 写法1
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        //EasyExcel.write(response.getOutputStream(), DemoData.class)
        //        .sheet("模板")
        //        .doWrite(() -> {
        //            // 分页查询数据
        //            return simpleData();
        //        });

        // 写法2
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        /**
         * 最简单的写
         */
        //EasyExcel.write(response.getOutputStream(), DemoData.class).sheet("模板").doWrite(simpleData());
        /**
         * 日期、数字或者自定义格式转换
         */
        //EasyExcel.write(response.getOutputStream(), ConverterData.class).sheet("模板").doWrite(simpleData());
        /**
         * 自动列宽(不太精确)
         *
         * 这个目前不是很好用，比如有数字就会导致换行。而且长度也不是刚好和实际长度一致。 所以需要精确到刚好列宽的慎用。 当然也可以自己参照
         */
        EasyExcel.write(response.getOutputStream(), ConverterData.class).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("模板").doWrite(simpleData());

        // 写法3
        // 这里 需要指定写用哪个class去写
        //try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), DemoData.class).build()) {
        //    WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        //    excelWriter.write(simpleData(), writeSheet);
        //}

    }

    private List <DemoData> simpleData() {
        List <DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * web中的写并且失败的时候返回json
     *
     * https://alibaba-easyexcel.github.io/quickstart/write.html#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson
     *
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     *
     * @since 2.1.1
     */
    @GetMapping("/downloadFailedUsingJson")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream()).head(head()).autoCloseStream(Boolean.FALSE).sheet(
                    "模板").doWrite(dataList());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map <String, String> map = new HashMap <String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /* private methods ----------------------------------------------------- */
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
        head0.add("字符串哈哈哈" + System.currentTimeMillis());
        List <String> head1 = new ArrayList <String>();
        head1.add("数字哈哈哈" + System.currentTimeMillis());
        List <String> head2 = new ArrayList <String>();
        head2.add("日期哈哈哈" + System.currentTimeMillis());
        List <String> head3 = new ArrayList <String>();
        head3.add("Foobar哈哈哈" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);
        return list;
    }

    private static List <List <String>> head2() {
        List <List <String>> list = new ArrayList <List <String>>();
        List <String> head0 = new ArrayList <String>();
        head0.add("字符串啊啊啊" + System.currentTimeMillis());
        List <String> head1 = new ArrayList <String>();
        head1.add("数字啊啊啊" + System.currentTimeMillis());
        List <String> head2 = new ArrayList <String>();
        head2.add("日期啊啊啊" + System.currentTimeMillis());
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

    private static List <List <Object>> dataList1() {
        List <List <Object>> list = new ArrayList <List <Object>>();
        for (int i = 0; i < 10; i++) {
            List <Object> data = new ArrayList <Object>();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(0.56);
            data.add(0.56);
            list.add(data);
        }
        return list;
    }

    private static List <List <Object>> dataList2() {
        List <List <Object>> list = new ArrayList <List <Object>>();
        for (int i = 0; i < 10; i++) {
            List <Object> data = new ArrayList <Object>();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(new Date());
            data.add(0.56);
            list.add(data);
        }
        return list;
    }

    /* getters/setters ----------------------------------------------------- */

}
