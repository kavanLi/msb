package com.mashibing.internal.common.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-02 00:55
 * To change this template use File | Settings | File and Code Templates.
 */
@Documented
@Constraint(validatedBy = MapSizeRangeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MapSizeRange {


    String message() default "Map集合元素数量必须在 {min} 和 {max} 之间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 1;

    int max() default 10;
}
