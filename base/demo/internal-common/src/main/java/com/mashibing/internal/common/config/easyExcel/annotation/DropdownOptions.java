package com.mashibing.internal.common.config.easyExcel.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: kavanLi-R7000
 * @create: 2024-07-22 15:58
 * To change this template use File | Settings | File and Code Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DropdownOptions {

    /**
     * enumClass 优先级高于 value
     * 检查 enumClass 注解值，如果不是 DefaultEnumClass，则通过反射获取枚举类的所有枚举实例，并提取其描述。如果是 DefaultEnumClass，则直接使用注解的 value 属性。
     */
    Class<? extends Enum<?>> enumClass() default DefaultEnumClass.class;
    String[] value() default {""};
    boolean check() default true; // 新增的 check 属性

    // 定义一个默认的占位符类
    enum DefaultEnumClass {}
}