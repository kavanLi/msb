package com.msb.testServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Servlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();
        // 获取web.xml中配置的全局的初始信息
        Enumeration<String> pnames = servletContext.getInitParameterNames();
        while(pnames.hasMoreElements()){
            String e = pnames.nextElement();
            System.out.println(e+":"+servletContext.getInitParameter(e));
        }


        List<String> list = (List<String>) servletContext.getAttribute("list");
        System.out.println(list);
        String gender = (String)servletContext.getAttribute("gender");
        System.out.println(gender);
    }
}
