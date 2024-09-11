package com.msb.es.dto.enums;

public enum FieldType {
    /**
     * text
     */
    TEXT("text"),

    KEYWORD("keyword"),

    INTEGER("integer"),

    DOUBLE("double"),

    DATE("date"),

    /**
     * 单条数据
     */
    OBJECT("object"),

    /**
     * 嵌套数组
     */
    NESTED("nested"),


    ;


    FieldType(String type){
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }
}
