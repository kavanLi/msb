package com.mashibing.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebServlet(urlPatterns = "/servlet3.do")
public class Servlet3 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 如果是第一访问当前Servlet.向浏览器响应一个cookie ("servlet3","1")
        // 如果是多次访问,就再次数上+1
        Cookie[] cookies = req.getCookies();

        boolean  flag =false ;

        if(null !=cookies){
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if(cookieName.equals("servlet3")){
                    // 创建Cookie次数+1
                    Integer value = Integer.parseInt(cookie.getValue())+1;
                    Cookie c=new Cookie("servlet3", String.valueOf(value));
                    resp.addCookie(c);
                    System.out.println("欢迎您第"+value+"访问");
                    flag=true;
                }
            }
        }

        if(!flag){
            System.out.println("欢迎您第一次访问");
            Cookie c=new Cookie("servlet3", "1");
            resp.addCookie(c);
        }

    }
}
