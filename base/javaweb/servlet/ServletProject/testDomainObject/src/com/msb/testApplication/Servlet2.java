package com.msb.testApplication;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebServlet(urlPatterns="/readFromApplication.do")
public class Servlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext application = this.getServletContext();


        // 从application域中读取数据
        List<String> list =(List<String>) application.getAttribute("list");
        System.out.println(list);
        System.out.println(application.getAttribute("gender"));
        System.out.println(application.getAttribute("name"));


    }
}
