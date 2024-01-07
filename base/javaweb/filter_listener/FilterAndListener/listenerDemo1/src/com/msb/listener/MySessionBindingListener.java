package com.msb.listener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
/*
* 可以监听具体的某个session对象的事件的
*
*  HttpSessionListener 只要在web.xml中配置或者通过@WebListener注解就可以注册监听所有的Session对象
* HttpSessionBindingListener 必须要通过setAttribute方法和某个session对象绑定之后,监听单独的某个Session对象
* */
public class MySessionBindingListener implements HttpSessionBindingListener {
    // 绑定方法
    /*
    session.setAttribute("mySessionBindingListener",new MySessionBindingListener())
     */
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("监听器和某个session对象绑定了");
    }
    // 解除绑定方法
    /*
    * 1 session.invalidate(); 让session不可用
    * 2 session到达最大不活动时间,session对象回收 ;
    * 3 session.removeAttribute("mySessionBindingListener");手动解除绑定
    * */
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {

    }
}
