package com.mashibing.internal.common.config.dataSource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-25 19:50
 * To change this template use File | Settings | File and Code Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ConfigDB {
    String value() default "";
}
