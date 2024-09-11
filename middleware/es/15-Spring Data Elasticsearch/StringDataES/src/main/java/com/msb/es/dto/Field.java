package com.msb.es.dto;



import com.msb.es.dto.enums.AnalyzerType;
import com.msb.es.dto.enums.FieldType;

import java.lang.annotation.*;

/**
 * @author qinlei
 * @description todo
 * @date 2021/3/20 16:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Field {

    FieldType type() default FieldType.TEXT;

    /**
     * 指定分词器
     * @return
     */
    AnalyzerType analyzer() default AnalyzerType.STANDARD;
}