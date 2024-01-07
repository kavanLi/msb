package com.mashibing.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebServlet(urlPatterns = "/servlet1.do")
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 通过响应对象,向浏览器响应一些Cookie

        Cookie c1=new Cookie("age","10");// 状态Cookie 重启即清除
        Cookie c2=new Cookie("gender", "男");//持久化Cookie 让浏览器保留1分钟
        //c2.setMaxAge(60);// 秒钟    持久化Cookie 让浏览器保留1分钟
        resp.addCookie(c1);
        resp.addCookie(c2);

    }
}
