package com.msb.controller;

import com.msb.pojo.User;

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

@WebServlet(urlPatterns = "/loginController.do")
public class LoginController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取用户名和密码
        String username = req.getParameter("user");
        String password = req.getParameter("pwd");
        System.out.println(username);
        System.out.println(password);
        // 链接数据库校验登录
        // 登录成功,将用户信息放入Session域
        User user =new User(username,password);
        req.getSession().setAttribute("user", user);
        // 跳转到欢迎页
        resp.sendRedirect("welcome.jsp");

    }
}
