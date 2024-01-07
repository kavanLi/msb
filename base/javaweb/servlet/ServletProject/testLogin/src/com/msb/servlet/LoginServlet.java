package com.msb.servlet;

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
@WebServlet(urlPatterns = "/loginServlet.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 如果用户名和密码为 msb 1234

        if("msb".equals(username)  && "1234".equals(password)){
            // 将用户信息放在HTTPSession中
            User user =new User(null, null, "msb", "1234");
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            // 登录成功 跳转至 main.html
            resp.sendRedirect(req.getContextPath()+"/mainServlet.do");

        }else{
            // 登录失败 回到login.html
            resp.sendRedirect(req.getContextPath()+"/login.html");
        }






    }
}
