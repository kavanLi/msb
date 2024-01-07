package com.msb.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

@WebListener
public class MySessionListener implements HttpSessionListener , HttpSessionAttributeListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("任何一个Session对象创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("任何一个Session对象的销毁");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        System.out.println("任何一个Session对象中添加了数据");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        System.out.println("任何一个Session对象中移除了数据");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        System.out.println("任何一个Session对象中修改了数据");
    }
}
