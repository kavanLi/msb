package com.msb.test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
//@WebServlet(urlPatterns = "/c/c2/servlet2.do")
@WebServlet(urlPatterns = "/servlet2.do")
public class Servlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 响应重定向到a1.html
        /*
        * 相对路径 相对于urlPatterns定义的路径
        * 绝对路径 :以项目部署路径为基准路径 webapps
        * 响应重定向的绝对路径中,要加项目部署名,除非当前项目没有给定部署名
        *
        * */
       // resp.sendRedirect("../../a/a2/a1.html");
        //resp.sendRedirect("a/a2/a1.html");
        ServletContext servletContext = this.getServletContext();
        String contextPath = servletContext.getContextPath();//  /testServlet4_war_exploded
        resp.sendRedirect(contextPath+"/a/a2/a1.html");
    }
}
