package com.msb.es.dto;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Document {
    /**
     * index : 索引名称
     * @return
     */
    String indexName();
}