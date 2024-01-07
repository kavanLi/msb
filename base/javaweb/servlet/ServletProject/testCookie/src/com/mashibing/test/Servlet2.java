package com.mashibing.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebEndpoint;
import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebServlet(urlPatterns = "/servlet2.do")
public class Servlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 读取请求中的Cookie
        Cookie[] cookies = req.getCookies();
        //cookies不为null
        if(null != cookies){
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName()+"="+cookie.getValue());
            }
        }
    }
}
