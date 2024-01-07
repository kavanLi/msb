package com.msb.testRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebServlet(urlPatterns="/readFromRequest.do")
public class Servlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 移除域中的互数据
        req.removeAttribute("gender");

        // 从request域中读取数据
        List<String> list =(List<String>) req.getAttribute("list");
        System.out.println(list);
        System.out.println(req.getAttribute("gender"));
        System.out.println(req.getAttribute("name"));
        //获取Request中的请求参数
        System.out.println(req.getParameter("username"));
        System.out.println(req.getParameter("password"));

    }
}
