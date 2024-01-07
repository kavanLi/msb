package com.mashibing.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class MyServlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置响应码
        //resp.setStatus(500);
        //resp.setStatus(405, "request method not supported");

        // 设置响应头
        //resp.setHeader("Date","2022-11-11");
        // 自定义头
        // resp.setHeader("aaa", "bbb");
        // 高速浏览器响应的数据是什么? 浏览器根据此头决定 数据如何应用
        // 设置MIME类型 json  xml 文件下载  ... ...
        // resp.setHeader("content-type", "text/css");
        resp.setContentType("text/html");// 专门用于设置Content-Type 响应头

        resp.getWriter().write("<h1>this is tag h1</h1>");


    }
}
