package com.msb.test;

import javax.servlet.RequestDispatcher;
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
@WebServlet(urlPatterns = "/c/c2/servlet1.do")
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        * 请求转发的路径写法
        * 相对基准路径:相对于当前Servlet本身的位置,urlPattern决定了位置
        * 绝对基准路径:永远是以项目为基准路径(不允许跨服务,所以绝对路径只能是本服务内的资源)
        *
        * */
        /*相对路径访问a1.html*/
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("a/a2/a1.html");
        /*urlpatterns影响相对路径*/
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("../../a/a2/a1.html");
        /*绝对路径访问a1*/
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/a/a2/a1.html");
        requestDispatcher.forward(req,resp);
    }
}
