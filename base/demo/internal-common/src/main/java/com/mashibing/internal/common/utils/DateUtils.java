package com.mashibing.internal.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;


/**
 * 日期工具类,
 * 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author init
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {


    public static final DateTimeFormatter excelExportdateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd" +
            "-HH_mm_ss");

    private static final long ONE_MILLIS = 1000;
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;
    public static final String datePattern = "yyyyMMddHHmmss";

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_CHS = "yyyy年MM月dd日";
    public static final String DATE_SHORT_FORMAT = "yyyyMMdd";
    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    public static final String TIMESTAMP_MILLISECOND_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM", "yyyyMMddHHmmssSSS"};

    /**
     * 日期型字符串转化为日期 格式
     * {
     * "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
     * "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM"}
     */
    public static Date parseDate(String str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str, parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * yyyyMMddHHmmss转化为日期 格式
     *
     * @param str
     * @return
     */
    public static Date parseDateAnother(String str) {
        if (str == null) {
            return null;
        }
        try {
            String[] parsePatterns = {datePattern};
            return parseDate(str, parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String formatCurrentDate() {
        return formatCurrentDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatCurrentDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (pattern != null) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }


    /**
     * 获取当前时间戳（yyyyMMddHHmmss）
     *
     * @return nowTimeStamp
     */
    public static long getCurrentTimestamp() {
        long nowTimeStamp = Long.parseLong(getCurrentTimestampStr());
        return nowTimeStamp;
    }

    /**
     * 获取当前时间戳（yyyyMMddHHmmss）
     *
     * @return
     */
    public static String getCurrentTimestampStr() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取当前时间戳（yyyyMMddHHmmss）
     *
     * @return
     */
    public static String getCurrentTimestampfffStr() {
        return formatDate(new Date(), "yyyyMMddHHmmssSSS");
    }

    /**
     * 获取Unix时间戳
     *
     * @return
     */
    public static long getCurrentUnixTimestamp() {
        long nowTimeStamp = System.currentTimeMillis() / 1000;
        return nowTimeStamp;
    }

    /**
     * 获取Unix时间戳
     *
     * @return
     */
    public static String getCurrentUnixTimestampStr() {
        return String.valueOf(getCurrentUnixTimestamp());
    }

    /**
     * 转换Unix时间戳
     *
     * @return nowTimeStamp
     */
    public static long parseUnixTimeStamp(long time) {
        return time / ONE_MILLIS;
    }

    /**
     * 获取前一周
     *
     * @param date
     * @return
     */
    public static Date getBeforeWeek(Date date) {
        return getAddDate(date, Calendar.WEEK_OF_YEAR, -1);
    }

    /**
     * 获取前一天
     *
     * @param date
     * @return
     */
    public static Date getBeforeDay(Date date) {
        return getAddDate(date, Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * 获取前一月
     *
     * @param date
     * @return
     */
    public static Date getBeforeMouth(Date date) {
        return getAddDate(date, Calendar.MONTH, -1);
    }

    /**
     * 获取前一年
     *
     * @param date
     * @return
     */
    public static Date getBeforeYear(Date date) {
        return getAddDate(date, Calendar.YEAR, -1);
    }


    /**
     * 获取前一周
     *
     * @param date
     * @return
     */
    public static Date getAfterWeek(Date date) {
        return getAddDate(date, Calendar.WEEK_OF_YEAR, 1);
    }

    /**
     * 获取前一天
     *
     * @param date
     * @return
     */
    public static Date getAfterDay(Date date) {
        return getAddDate(date, Calendar.DAY_OF_YEAR, 1);
    }

    /**
     * 获取前一月
     *
     * @param date
     * @return
     */
    public static Date getAfterMouth(Date date) {
        return getAddDate(date, Calendar.MONTH, 1);
    }

    /**
     * 获取前一年
     *
     * @param date
     * @return
     */
    public static Date getAfterYear(Date date) {
        return getAddDate(date, Calendar.YEAR, 1);
    }


    /**
     * 增加日期
     *
     * @param date
     * @param field  Calendar.MONTH,Calendar.DAY_OF_YEAR
     * @param amount 正数为将来时间, 负数为过去时间
     * @return
     */
    public static Date getAddDate(Date date, int field, int amount) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(field, amount);
        Date dateFrom = cl.getTime();
        return dateFrom;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String formatTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatCurrentDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String formatYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String formatMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String formatDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String formatWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long getBeforeDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (ONE_DAY * ONE_MILLIS);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long getBeforeHours(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (ONE_HOUR * ONE_MILLIS);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long getBeforeMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (ONE_MINUTE * ONE_MILLIS);
    }

    /**
     * 获取过去的秒
     *
     * @param date
     * @return
     */
    public static long getBeforeSeconds(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / ONE_MILLIS;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDays(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (double) (afterTime - beforeTime) / (double) (ONE_MILLIS * ONE_DAY);
    }


    /**
     * 距离今天多久
     *
     * @param createAt
     * @return
     */
    public static String formatTextFromtoday(Date createAt) {
        // 定义最终返回的结果字符串。
        String interval = null;
        if (createAt == null) {
            return "";
        }
        long millisecond = System.currentTimeMillis() - createAt.getTime();

        long second = millisecond / ONE_MILLIS;

        if (second <= 0) {
            second = 0;
        }
        //*--------------微博体（标准）
        if (second == 0) {
            interval = "刚刚";
        } else if (second < ONE_MINUTE / 2) {
            interval = second + "秒以前";
        } else if (second >= ONE_MINUTE / 2 && second < ONE_MINUTE) {
            interval = "半分钟前";
        } else if (second >= ONE_MINUTE && second < ONE_MINUTE * ONE_MINUTE) {
            //大于1分钟 小于1小时
            long minute = second / ONE_MINUTE;
            interval = minute + "分钟前";
        } else if (second >= ONE_HOUR && second < ONE_DAY) {
            //大于1小时 小于24小时
            long hour = (second / ONE_MINUTE) / ONE_MINUTE;
            interval = hour + "小时前";
        } else if (second >= ONE_DAY && second <= ONE_DAY * 2) {
            //大于1D 小于2D
            interval = "昨天" + formatDate(createAt, "HH:mm");
        } else if (second >= ONE_DAY * 2 && second <= ONE_DAY * 7) {
            //大于2D小时 小于 7天
            long day = ((second / ONE_MINUTE) / ONE_MINUTE) / 24;
            interval = day + "天前";
        } else if (second <= ONE_DAY * 365 && second >= ONE_DAY * 7) {
            //大于7天小于365天
            interval = formatDate(createAt, "MM-dd HH:mm");
        } else if (second >= ONE_DAY * 365) {
            //大于365天
            interval = formatDate(createAt, "yyyy-MM-dd HH:mm");
        } else {
            interval = "0";
        }
        return interval;
    }


    /**
     * 距离截止日期还有多长时间
     *
     * @param date
     * @return
     */
    public static String formatTextFromDeadline(Date date) {
        long deadline = date.getTime() / ONE_MILLIS;
        long now = (System.currentTimeMillis()) / ONE_MILLIS;
        long remain = deadline - now;
        if (remain <= ONE_HOUR) {
            return "只剩下" + remain / ONE_MINUTE + "分钟";
        } else if (remain <= ONE_DAY) {
            return "只剩下" + remain / ONE_HOUR + "小时"
                    + (remain % ONE_HOUR / ONE_MINUTE) + "分钟";
        } else {
            long day = remain / ONE_DAY;
            long hour = remain % ONE_DAY / ONE_HOUR;
            long minute = remain % ONE_DAY % ONE_HOUR / ONE_MINUTE;
            return "只剩下" + day + "天" + hour + "小时" + minute + "分钟";
        }

    }


    /**
     * Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param pattern         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String unixTimeStamp2Date(String timestampString, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * ONE_MINUTE;
        String date = new SimpleDateFormat(pattern, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * 日期格式字符串转换成Unix时间戳
     *
     * @param dateStr 字符串日期
     * @param pattern 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2UnixTimeStamp(String dateStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return String.valueOf(sdf.parse(dateStr).getTime() / ONE_MINUTE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 得到当前日期时间
     *
     * @return yyyyMMddHHmmss
     */
    public static String getCurrDateTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    public static String getCurDateTime() {
        Date today = new Date();
        return format(today, datePattern);
    }

    public static Date parse(String strDate, String pattern) throws ParseException {
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }


    public static String getCurrentDate() {
        return format(new Date(), parsePatterns[12]);
    }

    /**
     * 返回 当前日期字符串,到毫秒
     */
    public static String getCurDateTimeM() {
        Date today = new Date();
        return format(today, parsePatterns[16]);
    }

    /**
     * 检查是否是一个合法的日期格式
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isValidDateFormat(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        // 是否自动计算超出日期
        format.setLenient(false);
        try {
            format.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 获取本月第一天
     * @return
     */
    public static String getFirstDayOfMonth(){
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.withDayOfMonth(1);
        return localDate.format(DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT));
    }

    public static String getFirstDayOfWeek(){
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int i = dayOfWeek.getValue() - DayOfWeek.MONDAY.getValue();
        LocalDate localDate = now.minusDays(i);
        return localDate.format(DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT));
    }

    public static int getLastDayOfMonth(int year,int month){
        LocalDate localDate = LocalDate.of(year, month, 1);
        return localDate.lengthOfMonth();
    }


    public static void main(String[] args) {
        System.out.println(formatDate(getBeforeDay(new Date()), "yyyy-MM-dd"));
        System.out.println(formatDate(getBeforeWeek(new Date()), "yyyy-MM-dd"));
        System.out.println(formatDate(getBeforeYear(new Date()), "yyyy-MM-dd"));
        System.out.println(formatDate(getAfterDay(new Date()), "yyyy-MM-dd"));
        System.out.println(formatDate(getAfterWeek(new Date()), "yyyy-MM-dd"));
        System.out.println(formatDate(getAfterYear(new Date()), "yyyy-MM-dd"));
    }
}
