package com.example.demo.designpattern.singleton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 枚举单例不仅可以解决线程同步，还可以防止反序列化。
 * 枚举类本身是线程安全的，因为枚举实例是在类加载时创建的，并且JVM保证了类加载过程的线程安全。
 *
 * @author: kavanLi-R7000
 * @create: 2024-03-28 15:04
 * To change this template use File | Settings | File and Code Templates.
 */
public enum CustomDateTimeFormatter {
    YYYY_MM_DD("yyyyMMdd"),
    YYYY_MM_DD_WHIPPTREE("yyyy-MM-dd"),
    HH_MM_SS("HHmmss"),
    YYYY_MM_DD_HH_MM_SS("yyyyMMddHHmmss"),
    YYYY_MM_DD_HH_MM_SSSSS("yyyyMMddHHmmssSSS"),

    ;
    private final String pattern;
    private final DateTimeFormatter dateTimeFormatter;


    /**
     * 在构造函数中初始化 dateTimeFormatter 的方式不会引起线程安全问题，因为在 Java 中，对象的构造过程是线程安全的。这意味着当一个线程正在执行构造函数时，其他线程无法同时访问该对象，因此不会出现多个线程同时初始化 dateTimeFormatter 的情况。
     *
     * 因此，您的代码中的构造函数是线程安全的。每个 CustomDateTimeFormatter 实例在构造时都会创建一个独立的 DateTimeFormatter 实例，因此每个实例都是线程安全的。
     * @param pattern
     */
    CustomDateTimeFormatter(String pattern) {
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
        DateTimeFormatter formatter1 = CustomDateTimeFormatter.YYYY_MM_DD.getInstance();
        DateTimeFormatter formatter2 = CustomDateTimeFormatter.HH_MM_SS.getInstance();
        DateTimeFormatter formatter3 = CustomDateTimeFormatter.YYYY_MM_DD.getInstance();
        DateTimeFormatter formatter4 = CustomDateTimeFormatter.HH_MM_SS.getInstance();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(CustomDateTimeFormatter.YYYY_MM_DD.getPatter()));

        System.out.println(formatter1.format(LocalDateTime.now()));
        System.out.println(formatter2.format(LocalDateTime.now()));
        System.out.println(formatter1.hashCode());
        System.out.println(formatter2.hashCode());
        System.out.println(formatter3.hashCode());
        System.out.println(formatter4.hashCode());
    }

}
