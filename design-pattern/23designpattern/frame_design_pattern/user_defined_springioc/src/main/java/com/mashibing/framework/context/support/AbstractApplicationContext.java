package com.mashibing.framework.context.support;

import com.mashibing.framework.beans.factory.support.BeanDefinitionReader;
import com.mashibing.framework.beans.factory.support.BeanDefinitionRegistry;
import com.mashibing.framework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * ApplicationContext接口的子类实现类
 *         创建容器对象时,加载配置文件,对bean进行初始化
 * @author spikeCong
 * @date 2022/10/28
 **/
public abstract class AbstractApplicationContext implements ApplicationContext {

    //声明解析器变量
    protected BeanDefinitionReader beanDefinitionReader;

    //定义一个存储bean对象的Map集合
    protected Map<String,Object> singletonObjects = new HashMap<>();

    //声明一个配置文件路径变量
    protected String configLocation;

    @Override
    public void refresh() {

        //加载BeanDefinition对象
        try {
            beanDefinitionReader.loadBeanDefinitions(configLocation);

            //初始化
            finishInitialization();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //bean初始化
    protected void finishInitialization() throws Exception {
        //获取注册表对象
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();

        //获取BeanDefinition对象
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String definitionName : beanDefinitionNames) {
            getBean(definitionName);
        }
    };
}
