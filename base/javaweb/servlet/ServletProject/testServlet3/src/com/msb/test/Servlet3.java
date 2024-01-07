package com.msb.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebServlet(urlPatterns = "/servlet3.do")
public class Servlet3 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servlet3 service invoked");
        String money = req.getParameter("money");
        System.out.println("money:"+money);
        // 响应重定向
        resp.sendRedirect("servlet4.do?money="+money);
        //resp.sendRedirect("WEB-INF/bbb.html");
        //resp.sendRedirect("https://www.baidu.com");

        /*
         * 响应重定向总结
         * 1重定向是服务器给浏览器重新指定请求方向 是一种浏览器行为 地址栏会发生变化
         * 2重定向时,请求对象和响应对象都会再次产生,请求中的参数是不会携带
         * 3重定向也可以帮助我们完成页面跳转
         * 4重定向不能帮助我们访问WEB-INF中的资源
         * 5重定向可以定向到外部资源
         *
         * */
    }
}
