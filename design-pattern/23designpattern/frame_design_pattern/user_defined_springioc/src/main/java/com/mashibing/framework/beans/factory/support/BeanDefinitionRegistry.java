package com.mashibing.framework.beans.factory.support;

import com.mashibing.framework.beans.BeanDefinition;

/**
 * 注册表对象
 * @author spikeCong
 * @date 2022/10/28
 **/
public interface BeanDefinitionRegistry {

    //注册BeanDefinition对象到注册表
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    //从注册表删除指定名称的BeanDefinition对象
    void removeBeanDefinition(String beanName);

    //根据名称获取注册表中的对应的BeanDefinition
    BeanDefinition getBeanDefinition(String beanName);

    //判断注册表中是否包含指定名称的BeanDefinition对象
    boolean containsBeanDefinition(String beanName);

    //获取注册表中所有的BeanDefinition的对象名称
    String[] getBeanDefinitionNames();

    //获取注册表中BeanDefinition对象的个数
    int getBeanDefinitionCount();
}
