package com.msb.testApplication;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebServlet(urlPatterns = "/addToApplication.do")
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 向Application域中添加数据
        ServletContext application = req.getServletContext();
        List<String> x=new ArrayList<>();
        Collections.addAll(x, "a","b","c");
        application.setAttribute("list", x);
        application.setAttribute("gender","girl");
        application.setAttribute("name","晓明");

    }
}
