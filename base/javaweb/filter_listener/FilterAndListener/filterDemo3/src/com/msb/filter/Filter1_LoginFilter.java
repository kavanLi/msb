package com.msb.filter;

import com.msb.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebFilter(urlPatterns = "/*")// 任何资源都要进行过滤,
public class Filter1_LoginFilter  implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;

        //无论是否登录过,都要放行的资源   登录页  登录校验Controller 和一些静态资源
        String requestURI = req.getRequestURI();
        System.out.println(requestURI);
        if(requestURI.contains("login.jsp")|| requestURI.contains("loginController.do")|| requestURI.contains("/static/")){
            // 直接放行
            filterChain.doFilter(req,resp);
            // 后续代码不再执行
            return;
        }

        // 需要登录之后才能访问的资源,如果没登录,重定向到login.jsp上,提示用户进行登录
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        if(null != user){// 如果登录过 放行
            filterChain.doFilter(req,resp);

        }else{// 没有登录过,跳转至登录页
            resp.sendRedirect("login.jsp");
        }


    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
