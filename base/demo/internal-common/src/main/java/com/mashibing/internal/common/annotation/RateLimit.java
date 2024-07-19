package com.mashibing.internal.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: kavanLi-R7000
 * @create: 2024-06-26 14:52
 * To change this template use File | Settings | File and Code Templates.
 * 自定义限流注解
 */
@Target(ElementType.METHOD) // 该注解只能用于方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时保留该注解信息
public @interface RateLimit {
    int permitsPerSecond() default 1; // 默认每秒限制1个请求
    int interval() default 3; // 默认每3秒允许一次请求
}
