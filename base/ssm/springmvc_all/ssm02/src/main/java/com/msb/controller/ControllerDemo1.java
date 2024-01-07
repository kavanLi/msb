package com.msb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
public class ControllerDemo1 {
    @RequestMapping("test1.action")
    public String test1(){
        int i = 1/0;
        return "success.jsp";
    }
    @RequestMapping("test2.action")
    public String test2(){
        String s =null;
        System.out.println(s.length());
        return "success.jsp";
    }

}
