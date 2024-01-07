package com.msb.controller;

import com.msb.listener.MySessionActivationListener;
import com.msb.pojo.User;

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
@WebServlet("/loginController.do")
public class LoginController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String pwd = req.getParameter("pwd");
        // user
        User user =new User(username,pwd);

        // session
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        // 绑定监听器
        session.setAttribute("listener", new MySessionActivationListener());

    }
}
