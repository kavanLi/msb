package com.mashibing.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebServlet(urlPatterns = "/servlet2.do")
public class Servlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取HTTPSession
        HttpSession session = req.getSession();
        // 尝试从HTTPSession中获取数据
        String username = (String)session.getAttribute("username");
        String password = (String)session.getAttribute("password");
        String level = (String)session.getAttribute("level");

        System.out.println(username);
        System.out.println(password);
        System.out.println(level);

        // 获取Session对象的其他信息
        System.out.println("创建时间:"+session.getCreationTime());
        System.out.println("最后一次访问时间:"+session.getLastAccessedTime());
        System.out.println("最大不活动时间:"+session.getMaxInactiveInterval());
    }
}
