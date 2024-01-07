package com.msb.testServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

@WebServlet(
        urlPatterns={"/servlet2.do","/a.do","/b.do","/c.do"},
        loadOnStartup = 6,
        initParams = {
                @WebInitParam(name="brand",value = "asus"),
                @WebInitParam(name="screen",value = "京东方")
        }
        )
public class Servlet2 extends HttpServlet {
    public Servlet2(){
        System.out.println("Servlet2 Constructor invoked");
    }
    @Override
    public void init() throws ServletException {
        System.out.println("servlet1 inited");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet2 Service invoked");
        ServletConfig servletConfig = this.getServletConfig();
        System.out.println(servletConfig.getInitParameter("brand"));
        System.out.println(servletConfig.getInitParameter("screen"));
    }
}
