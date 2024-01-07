package com.msb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */


@WebFilter(urlPatterns = "/myServlet1.do",initParams = {@WebInitParam(name="realname",value ="zhangsan"),@WebInitParam(name="charset",value ="utf-8")})
public class Filter1_MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 获取初始化的一些参数
        String realname = filterConfig.getInitParameter("realname");
        System.out.println("realname:"+realname);

        Enumeration<String> pNames = filterConfig.getInitParameterNames();
        while(pNames.hasMoreElements()){
            String pName = pNames.nextElement();
            System.out.println(pName+":"+filterConfig.getInitParameter(pName));
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter1   在过滤请求 ");

        filterChain.doFilter(servletRequest,servletResponse);

        System.out.println("MyFilter1   在过滤响应");

    }

    @Override
    public void destroy() {

    }
}
