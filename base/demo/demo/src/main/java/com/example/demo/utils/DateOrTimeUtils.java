package com.example.demo.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author: kavanLi-R7000
 * @create: 2023-09-26 02:22
 * To change this template use File | Settings | File and Code Templates.
 */
public final class DateOrTimeUtils {

    private DateOrTimeUtils() {
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
