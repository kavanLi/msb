package com.mashibing.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class MyServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // req获取参数
        // 如果 前端发过来的数据由数据名但是没有值, getParameter返回的是一个空字符串  ""
        // 获取的参数在提交的数据中名都没有,getParameter返回的是null
        String username = req.getParameter("username");
        System.out.println("username:"+username);
        System.out.println("password:"+req.getParameter("pwd"));
        System.out.println("gender:"+req.getParameter("gender"));
        // hobby=1&hobby=2&hobby=3 想要获得多个同名的参数 getParameterValues 返回的是一个Sting数组
        String[] hobbies = req.getParameterValues("hobby");
        System.out.println("hobbies:"+ Arrays.toString(hobbies));

        // textarea
        System.out.println("introduce:"+req.getParameter("introduce"));

        // select
        System.out.println("provience:"+req.getParameter("provience"));

        System.out.println("___________________________");
        // 如果不知道参数的名字?
        // 获取所有的参数名
        Enumeration<String> pNames = req.getParameterNames();
        while(pNames.hasMoreElements()){
            String pname = pNames.nextElement();
            String[] pValues = req.getParameterValues(pname);
            System.out.println(pname+":"+Arrays.toString(pValues));
        }

        System.out.println("________________________________");
        Map<String, String[]> pmap = req.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = pmap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            System.out.println(entry.getKey()+":"+Arrays.toString(entry.getValue()));
        }


    }
}
