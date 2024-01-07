package com.msb.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class MyApplicationListener implements ServletContextListener , ServletContextAttributeListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext创建并初始化了");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext销毁了");
    }


    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        System.out.println("ServletContext增加了数据");
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        System.out.println("ServletContext删除了数据");
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        System.out.println("ServletContext修改了数据");

    }


}
