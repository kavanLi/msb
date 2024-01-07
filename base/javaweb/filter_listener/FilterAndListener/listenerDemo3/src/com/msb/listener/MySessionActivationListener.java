package com.msb.listener;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class MySessionActivationListener implements HttpSessionActivationListener, Serializable {
    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {

        System.out.println(se.getSession().hashCode()+"即将钝化");
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println(se.getSession().hashCode()+"已经活化");
    }
}
