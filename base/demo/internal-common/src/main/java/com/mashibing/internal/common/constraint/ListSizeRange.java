package com.mashibing.internal.common.constraint;

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
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListSizeRangeValidator.class)
public @interface ListSizeRange {


    String message() default "列表元素数量必须在 {min} 和 {max} 之间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 1;

    int max() default 10;
}
