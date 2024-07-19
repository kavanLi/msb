package com.mashibing.internal.common.utils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: kavanLi-R7000
 * @create: 2024-05-21 14:42
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public class UniqueIdUtil {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final Integer LENGTH_32_Bit = 32;
    private static final SecureRandom random = new SecureRandom();

    private static final String EPOCH_DATE_STRING = "2021-01-01";
    private static final String PREFIX = "SY"; // SY -> smartyunst

    public static String generateShortUuid(Integer length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public static String generateCustomLengthUuid(Integer length) {
        //StringBuilder sb = new StringBuilder(length);
        //for (int i = 0; i < length; i++) {
        //    sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        //}

        StringBuilder sb = new StringBuilder(length);

        Long snowflakeUuid = generateSnowflakeUuid();

        // 添加随机字符
        for (int i = 0; i < length - snowflakeUuid.toString().length(); i++) { // 减去雪花ID长度，确保总长度为 length
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        sb.append(snowflakeUuid);
        return sb.toString();
    }

    /**
     * B123320240611094000034
     * @param fixedLetter
     * @return
     */
    public static String generateLetterTimestampedID(String fixedLetter) {
        // 固定字母 fixedLetter

        // 4位随机数字
        int randomNum = random.nextInt(10000); // 确保生成的数字是4位

        // 时间戳格式
        String timestamp = CustomDateTimeFormatter.YYYY_MM_DD_HH_MM_SSSSS.getInstance().format(LocalDateTime.now());

        // 拼接生成ID
        return fixedLetter + String.format("%04d", randomNum) + timestamp;
    }


    /**
     * 生成全局唯一ID
     * @return
     */
    public static String generateGlobalUuid() {
        // 使用 Hutool 提供的 IdUtil 获取 Snowflake 实例
        long workerId = 1;
        long datacenterId = 1;

        // 自定义 epoch起始时间，假设为 2021-01-01 00:00:00 UTC
        //DateTimeFormatter formatter = DateOrTimeUtils.CustomDateTimeFormatter.YYYY_MM_DD_WHIPPTREE.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(EPOCH_DATE_STRING, formatter);
        Date customEpochDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 生成一些 ID 示例
        // 不使用系统时钟
        Snowflake snowflakeWithoutSystemClock = new Snowflake(customEpochDate, workerId, datacenterId, false);
        long id = snowflakeWithoutSystemClock.nextId();
        String uuid = PREFIX + id;
        log.info("Generated Snowflake ID: {}, with prefix: {}", id, uuid);
        return uuid;
    }

    public static Long generateSnowflakeUuid() {
        // 使用 Hutool 提供的 IdUtil 获取 Snowflake 实例
        long workerId = 1;
        long datacenterId = 1;

        // 自定义 epoch起始时间，假设为 2021-01-01 00:00:00 UTC
        //DateTimeFormatter formatter = DateOrTimeUtils.CustomDateTimeFormatter.YYYY_MM_DD_WHIPPTREE.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(EPOCH_DATE_STRING, formatter);
        Date customEpochDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 生成一些 ID 示例
        // 不使用系统时钟
        Snowflake snowflakeWithoutSystemClock = new Snowflake(customEpochDate, workerId, datacenterId, false);
        long id = snowflakeWithoutSystemClock.nextId();
        log.info("Generated Snowflake ID: {}", id);
        return id;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String shortUuid = generateCustomLengthUuid(LENGTH_32_Bit);
            System.out.println("Short UUID: " + shortUuid);
        }

        // 使用 Hutool 提供的 IdUtil 获取 Snowflake 实例
        long workerId = 1;
        long datacenterId = 1;

        // 自定义 epoch，假设为 2021-01-01 00:00:00 UTC
        long customEpoch = 1609459200000L;
        // 自定义 epoch，假设为 2021-01-01 00:00:00 UTC
        Date customEpochDate = new Date(customEpoch); // 2021-01-01 00:00:00 UTC

        // 生成一些 ID 示例
        // 不使用系统时钟
        Snowflake snowflakeWithoutSystemClock = new Snowflake(customEpochDate, workerId, datacenterId, false);
        System.out.println("Not using system clock:");
        for (int i = 0; i < 10; i++) {
            long id = snowflakeWithoutSystemClock.nextId();
            log.info("Generated Snowflake ID: {}, with prefix: {}", id, PREFIX + id);
        }

        //Snowflake defaultSnowflake = IdUtil.getSnowflake(workerId, datacenterId);
        //
        //// 生成一些 ID 示例
        //for (int i = 0; i < 10; i++) {
        //    long id = defaultSnowflake.nextId();
        //    System.out.println("Generated ID with default epoch: " + id);
        //}
    }
}
