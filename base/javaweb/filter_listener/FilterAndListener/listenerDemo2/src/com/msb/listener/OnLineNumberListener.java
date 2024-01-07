package com.msb.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebListener
public class OnLineNumberListener implements HttpSessionListener  {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // 向application域中 增加一个数字
        HttpSession session = se.getSession();
        ServletContext application = session.getServletContext();
        Object attribute = application.getAttribute("count");
        if(null == attribute){// 第一次放数据
            application.setAttribute("count", 1);
        }else{
            int count =(int)attribute;
            application.setAttribute("count", ++count);
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 向application域中 减少一个数字

        HttpSession session = se.getSession();
        ServletContext application = session.getServletContext();

        int count =(int)application.getAttribute("count");
        application.setAttribute("count", --count);

    }
}
