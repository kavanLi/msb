package com.mashibing.internal.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;


/**
 * @author: kavanLi-R7000
 * @create: 2023-09-26 02:22
 * To change this template use File | Settings | File and Code Templates.
 */
public class DateOrTimeUtils {

    private DateOrTimeUtils() {
    }
    public enum CustomDateTimeFormatterSingleton {
        YYYY_MM_DD("yyyyMMdd"),
        HH_MM_SS("HHmmss");

        private final String pattern;
        private final DateTimeFormatter dateTimeFormatter;


        /**
         * 在构造函数中初始化 dateTimeFormatter 的方式不会引起线程安全问题，因为在 Java 中，对象的构造过程是线程安全的。这意味着当一个线程正在执行构造函数时，其他线程无法同时访问该对象，因此不会出现多个线程同时初始化 dateTimeFormatter 的情况。
         *
         * 因此，您的代码中的构造函数是线程安全的。每个 CustomDateTimeFormatter 实例在构造时都会创建一个独立的 DateTimeFormatter 实例，因此每个实例都是线程安全的。
         * @param pattern
         */
        CustomDateTimeFormatterSingleton(String pattern) {
            this.pattern = pattern;
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        }

        public DateTimeFormatter getInstance() {
            return dateTimeFormatter;
        }

        public String getPatter() {
            return pattern;
        }

        public static void main(String[] args) {
            DateTimeFormatter formatter1 = DateOrTimeUtils.CustomDateTimeFormatterSingleton.YYYY_MM_DD.getInstance();
            DateTimeFormatter formatter2 = DateOrTimeUtils.CustomDateTimeFormatterSingleton.HH_MM_SS.getInstance();
            DateTimeFormatter formatter3 = DateOrTimeUtils.CustomDateTimeFormatterSingleton.YYYY_MM_DD.getInstance();
            DateTimeFormatter formatter4 = DateOrTimeUtils.CustomDateTimeFormatterSingleton.HH_MM_SS.getInstance();
            System.out.println(formatter1.format(LocalDateTime.now()));
            System.out.println(formatter2.format(LocalDateTime.now()));
            System.out.println(formatter1.hashCode());
            System.out.println(formatter2.hashCode());
            System.out.println(formatter3.hashCode());
            System.out.println(formatter4.hashCode());
        }

    }

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final org.joda.time.format.DateTimeFormatter jodaDateTimeFormatter =
            org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * SimpleDateFormat有诸多比如 线程不安全 性能 跨年转换异常等问题，建议替换成其他的工具类
     */
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        testDateFormatter();
    }

    /**
     * SimpleDateFormat完美的替代方案
     *
     * 推荐使用 DateTimeFormatter 结合 LocalDateTime 使用来格式化日期和时间。
     * 建议在 Java 代码中使用 LocalDateTime 取代 Date。
     *
     * 如果是 JDK8 的应用， 可以使用 Instant 代替 Date， LocalDateTime 代替 Calendar， DateTimeFormatter 代替
     * SimpleDateFormat， 官方给出的解释： simple beautiful strong immutable thread-safe。
     *
     */
    @SneakyThrows
    public static void testDateFormatter() {
        /**
         * DateTimeFormatter：localDateTime结合Java 8 引入了新的日期时间 API（java.time 包），其中的 DateTimeFormatter 类是线程安全的替代方案，推荐在新代码中使用。
         *
         * 相比 Date 的优势
         * Date 和 SimpleDateFormatter 非线程安全，而 LocalDate 和 LocalTime 和 String 一样，是final类型 - 线程安全且不能被修改。
         * Date 月份从0开始,一月是0，十二月是11。LocalDate 月份和星期都改成了 enum ，不会再用错。
         * Date是一个“万能接口”，它包含日期、时间，还有毫秒数。如果你只需要日期或时间那么有一些数据就没啥用。在新的Java 8中，日期和时间被明确划分为 LocalDate 和 LocalTime，LocalDate无法包含时间，LocalTime无法包含日期。当然，LocalDateTime才能同时包含日期和时间。
         * Date 推算时间(比如往前推几天/ 往后推几天/ 计算某年是否闰年/ 推算某年某月的第一天、最后一天、第一个星期一等等)要结合Calendar要写好多代码，十分恶心！
          */
        System.out.println("localDateTime + dateTimeFormatter");
        // 创建一个 LocalDateTime 对象
        LocalDateTime localDateTime = LocalDateTime.now();

        // 将 LocalDateTime 转换为字符串
        String dateTimeString = dateTimeFormatter.format(localDateTime);;
        System.out.println("LocalDateTime 转换为字符串: " + dateTimeString);

        // 将字符串转换为 LocalDateTime
        String dateString = "2024-02-20 10:30:15";
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, dateTimeFormatter);
        System.out.println("字符串转换为 LocalDateTime: " + parsedDateTime);
        System.out.println("---------------------------------\n");


        /**
         * SimpleDateFormat
         */
        System.out.println("simpleDateFormat");
        Date _date = simpleDateFormat.parse(dateTimeString);
        System.out.println("simpleDateFormat.parse: " + _date);
        String formatDate = simpleDateFormat.format(_date);
        System.out.println("simpleDateFormat.format: " + formatDate);
        System.out.println("---------------------------------\n");

        /**
         * joda-time 舍弃，转换出来的LocalDateTime不是java8自带的类型。
         * Joda-Time: Joda-Time 是一个功能强大的日期时间处理库，提供了线程安全、性能优良、格式化灵活的日期时间类。
          */
        System.out.println("joda-time");
        org.joda.time.LocalDateTime localDateTime1 = jodaDateTimeFormatter.parseLocalDateTime("2024-02-20 10:30:15");
        System.out.println("jodaDateTimeFormatter.parseLocalDateTime: " + localDateTime1);
        System.out.println("jodaDateTimeFormatter.print: " + jodaDateTimeFormatter.print(localDateTime1));
        System.out.println("---------------------------------\n");

        /**
         * FasterXML jackson-databind
         * FasterXML jackson-databind: FasterXML jackson-databind 是一个流行的 JSON 处理库，也提供了日期时间格式化功能。
         *
         */
        System.out.println("FasterXML jackson-databind");
        // 使用 ObjectMapper 将日期对象转换为 JSON 字符串

        // 创建一个 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();
        // 使用 ObjectMapper 将日期对象转换为 JSON 字符串
        String jsonDate = objectMapper.writeValueAsString(_date);
        System.out.println("objectMapper 日期对象转换为 JSON 字符串: " + jsonDate);

        // 使用 ObjectMapper 将 JSON 字符串转换为日期对象
        Date parsedDateFromJson = objectMapper.readValue(jsonDate, Date.class);
        System.out.println("objectMapper JSON 字符串转换为日期对象: " + parsedDateFromJson);
        System.out.println("---------------------------------\n");

    }


    public static void showZonedDateTime() {
        String zoneId = "Asia/Shanghai";

        // 获取某个时区当前时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(zoneId));
        // 获取指定时区开始时间
        ZonedDateTime startTime = ZonedDateTime.of(2022, 3, 16,
                0, 0, 01, 0, ZoneId.of(zoneId));
        // 获取指定时区结束时间
        ZonedDateTime endTime = ZonedDateTime.of(2022, 3, 16,
                23, 59, 59, 0, ZoneId.of(zoneId));
        System.out.println("获取指定时区当前时间：" + zonedDateTime);
        System.out.println("获取指定时区开始时间：" + startTime);
        System.out.println("获取指定时区结束时间：" + endTime);
    }


}
