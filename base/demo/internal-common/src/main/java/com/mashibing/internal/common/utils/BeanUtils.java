package com.mashibing.internal.common.utils;

import java.lang.reflect.Field;

import org.springframework.util.ObjectUtils;

/**
 * Bean工具类
 * @author Joyce Huang
 */
public class BeanUtils {

    /**
     * 忽略空值拷贝对象属性
     * @param sourceBean *
     * @param targetBean *
     * @param <T> *
     * @return *
     */
    public static <T> T mergeObjects(T sourceBean, T targetBean) {
        Class sourceBeanClass = sourceBean.getClass();
        Class targetBeanClass = targetBean.getClass();
        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = targetBeanClass.getDeclaredFields();
        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);

            for (Field targetField : targetFields) {
                if (targetField.getName().equals(sourceField.getName())) {
                    targetField.setAccessible(true);
                    try {
                        if (!ObjectUtils.isEmpty(sourceField.get(sourceBean))) {
                            targetField.set(targetBean, sourceField.get(sourceBean));
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return targetBean;
    }

    /**
     * 包含空值拷贝对象属性
     * @param sourceBean *
     * @param targetBean *
     * @param <T> *
     * @return *
     */
    public static <T> T mergeCompleteObjects(T sourceBean, T targetBean) {
        Class sourceBeanClass = sourceBean.getClass();
        Class targetBeanClass = targetBean.getClass();
        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = targetBeanClass.getDeclaredFields();
        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);

            for (Field targetField : targetFields) {
                if (targetField.getName().equals(sourceField.getName())) {
                    targetField.setAccessible(true);
                    try {
                        targetField.set(targetBean, sourceField.get(sourceBean));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return targetBean;
    }
}
