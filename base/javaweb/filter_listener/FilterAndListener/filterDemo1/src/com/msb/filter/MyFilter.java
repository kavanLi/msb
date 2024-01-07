package com.msb.filter;

import javax.servlet.*;

import java.io.IOException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

public class MyFilter implements Filter {

    public MyFilter(){
        System.out.println("MyFilter constructor invoked");
    }

    // 初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter init invoked");
    }
    // 作出过滤的方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter doFilter 对请求作出过滤");

        // 通过一行代码 放行请求
        // 放行请求,交给过滤器链继续进行过滤 最后到达资源
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("Filter doFilter 对响应作出过滤");

        servletResponse.getWriter().print("filter 追加一些数据");
    }

    // 销毁方法
    @Override
    public void destroy() {
        System.out.println("MyFilter destory invoked");

    }
}
