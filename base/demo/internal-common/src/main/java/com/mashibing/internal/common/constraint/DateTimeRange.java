package com.mashibing.internal.common.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeRangeValidator.class)
public @interface DateTimeRange {

    String pattern() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 在当前时间之后：isAfter
     * 在当前时间之前：isBefore
     * @return
     */
    String judge() default IS_AFTER;

    String message() default "日期不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 判断用户输入的参数在当前时间之前
     */
    public final static String IS_AFTER = "isAfter";

    /**
     * 判断用户输入的参数在当前时间之后
     */
    public final static String IS_BEFORE = "isBefore";
}
