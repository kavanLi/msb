package com.msb.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class MyInterceptor2 implements HandlerInterceptor {
    /**
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  目标要调用的Handler
     * @return 返回true放行,返回false拦截
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*在请求到达我们定义的handler之前工作的*/
        System.out.println("MyInterceptor2 preHandle");
        /*设置请求和响应的乱码 */
        /* request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");*/
        // 判断是否登录
        /*User user =(User) request.getSession().getAttribute("user");
        if(null == user)
            response.sendRedirect("index.jsp");
        return false;*/

        // 用户权限控制
        return true;
    }


    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView  controller响应的结果,视图和数据
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor2 postHandle");
        /*控制数据*/
        /*Map<String, Object> map = modelAndView.getModel();
        String msg = (String)map.get("msg");
        String newMsg = msg.replaceAll("脏话", "**");
        map.put("msg", newMsg);*/
        /*控制视图*/
        /*modelAndView.setViewName("/testDemo1.jsp");*/
    }


    /**
     * 无论controller是否出现异常,都会执行的方法
     *  一般来说都做一些资源释放工作
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*页面渲染完毕,但是还没有给浏览器响应数据的时候*/
        System.out.println("MyInterceptor2 afterCompletion");
        //System.out.println(ex);

    }
}
