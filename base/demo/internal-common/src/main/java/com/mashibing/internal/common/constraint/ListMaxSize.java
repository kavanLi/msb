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
@Constraint(validatedBy = ListMaxSizeValidator.class)
public @interface ListMaxSize {

    String message() default "列表最多只能包含 {value} 个元素";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
