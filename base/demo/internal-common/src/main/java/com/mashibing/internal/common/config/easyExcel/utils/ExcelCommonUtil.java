package com.mashibing.internal.common.config.easyExcel.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelCommonUtil {

    public static List <String> getExpectedHeaders(Class <?> clazz) {
        // 获取所有字段
        Field[] fields = clazz.getDeclaredFields();
        List <Field> fieldList = new ArrayList <>();

        // 过滤掉带有 @ExcelIgnore 注解的字段
        for (Field field : fields) {
            if (field.getAnnotation(ExcelIgnore.class) == null) {
                fieldList.add(field);
            }
        }

        // 根据 @ExcelProperty 注解的 order 属性排序
        List <Field> sortedFields = fieldList.stream()
                .filter(f -> f.getAnnotation(ExcelProperty.class) != null)
                .sorted(Comparator.comparingInt(f -> f.getAnnotation(ExcelProperty.class).order()))
                .collect(Collectors.toList());

        // 获取 @ExcelProperty 注解的 value 属性作为表头名称
        List <String> headers = sortedFields.stream()
                .map(f -> f.getAnnotation(ExcelProperty.class).value()[0])
                .collect(Collectors.toList());

        return headers;
    }

    /**
     * 获取字段@ExcelProperty的注解值
     */
    public static String getExcelPropertyValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                return String.join(", ", excelProperty.value());
            }
        } catch (NoSuchFieldException e) {
            log.error("Field not found: {}", fieldName, e);
        }
        return null;
    }

}
