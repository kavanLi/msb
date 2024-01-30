package com.mashibing.framework.beans.factory.support;

/**
 * 该接口定义了配置文件解析的规则
 * @author spikeCong
 * @date 2022/10/28
 **/
public interface BeanDefinitionReader {

    //获取注册表对象
    BeanDefinitionRegistry getRegistry();

    //加载配置文件并在注册表中进行注册
    void loadBeanDefinitions(String configLocation)throws Exception;

}
