package com.mashibing.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取请求行相关的数据   请求方式GET/POST  请求的资源路径  协议
        System.out.println("完整的URL:"+req.getRequestURL());//返回客户端浏览器发出请求时的完整URL。
        System.out.println("请求的指定资源:"+req.getRequestURI());//返回请求行中指定资源部分。
        System.out.println("客户端的IP:"+req.getRemoteAddr());//返回发出请求的客户机的IP地址。
        System.out.println("WEB服务器IP:"+req.getLocalAddr());//返回WEB服务器的IP地址。
        System.out.println("服务器端处理HTTP请求的端口:"+req.getLocalPort());//返回WEB服务器处理Http协议的连接器所监听的端口。
        System.out.println("主机名: " + req.getLocalName());
        System.out.println("客户端PORT: " + req.getRemotePort());
        System.out.println("当前项目部署名: " + req.getContextPath());
        System.out.println("协议名:"+req.getScheme());
        System.out.println("请求方式:"+req.getMethod());

        System.out.println("--------------------------------------");
        // 获取请求中所有的请求头
        String userAgent = req.getHeader("user-agent");
        System.out.println(userAgent);
        // 早期的Iterator
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            System.out.println(headerName+":"+req.getHeader(headerName));
        }
        System.out.println("--------------------------------------");


        // 获取请求中的数据
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        // 判断数据
        String message=null;
        if(username.equals("mashibing")&& pwd.equals("123456")){
            message="Success";
        }else{
            message="Fail";
        }
        // 作出响应
        resp.getWriter().write(message);
    }
}
