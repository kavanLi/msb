package com.msb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(String username, HttpServletRequest request){
        if(null != username&&!"".equals(username)){
            request.getSession().setAttribute("username", username);
            return "main";
        }
        return "redirect:/login.html";
    }
}
