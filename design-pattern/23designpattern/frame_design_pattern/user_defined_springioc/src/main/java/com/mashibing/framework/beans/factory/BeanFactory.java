package com.mashibing.framework.beans.factory;

/**
 * IOC容器的父接口
 * @author spikeCong
 * @date 2022/10/28
 **/
public interface BeanFactory {

    Object getBean(String name) throws Exception;

    //泛型方法
    <T> T getBean(String name,Class<? extends T> clazz);
}
