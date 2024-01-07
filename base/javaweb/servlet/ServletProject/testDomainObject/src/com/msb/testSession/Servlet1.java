package com.msb.testSession;

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
@WebServlet(urlPatterns = "/addToSession.do")
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 向Session域中添加数据
        HttpSession session = req.getSession();
        List<String> x=new ArrayList<>();
        Collections.addAll(x, "a","b","c");
        session.setAttribute("list", x);
        session.setAttribute("gender","boy");
        session.setAttribute("gender","girl");
        session.setAttribute("name","晓明");



        // 重定向
        resp.sendRedirect("readFromSession.do");
    }
}
