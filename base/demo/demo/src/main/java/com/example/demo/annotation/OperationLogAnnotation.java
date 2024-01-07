package com.example.demo.annotation;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-15 09:00
 * To change this template use File | Settings | File and Code Templates.
 */
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OperationLogAnnotation {
    boolean saveLog() default true;
}