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
@WebServlet(urlPatterns = "/servlet1.do")
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获得HttpSession对象  是一种保存更多数据在服务器端的一种技术
        // 一般保存当前登录 的用户
        // 用户的权限
        // 用户的其他信息 ... ...
        /*
        * getSession方法执行内容
        * 从request中尝试获取JSESSIONID的Cookie
        *
        *     A如果获取失败,
        *     认为上次会话已经结束,在这里要开启新的会话,创建一个新的HttpSession并返回
        *     将新的HttpSession对象的JSESSIONID以Cookie的形式放在Response对象,响应给浏览器
        *
        *     B如果获取成功
        *         根据JSESSIONID在服务器内找对应HttpSession对象
        *         1) 找到了,返回找到的HttpSession
        *         2) 没找到,创建新的HTTPSession并将SESSIONID以Cookie的形式放在Response对象,响应给浏览器
        *
        * */
        HttpSession httpSession=req.getSession();
        // 向HttpSession中存放一些数据
        httpSession.setAttribute("username", "msb");
        httpSession.setAttribute("password", "1234");
        httpSession.setAttribute("level", "A");

        //httpSession.invalidate();// 手动设置HTTPSession不可用   退出登录


    }
}
