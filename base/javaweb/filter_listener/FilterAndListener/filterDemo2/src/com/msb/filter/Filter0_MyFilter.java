package com.msb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@WebFilter(urlPatterns = "/myServlet1.do")
public class Filter0_MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter0   在过滤请求 ");

        filterChain.doFilter(servletRequest,servletResponse);

        System.out.println("MyFilter0   在过滤响应");

    }

    @Override
    public void destroy() {

    }
}
