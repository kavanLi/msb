package com.mashibing.framework.context;

import com.mashibing.framework.beans.factory.BeanFactory;

/**
 * 定义非延时加载功能
 * @author spikeCong
 * @date 2022/10/28
 **/
public interface ApplicationContext extends BeanFactory {

    //进行配置文件的加载,并进行对象的创建
    void refresh();
}
