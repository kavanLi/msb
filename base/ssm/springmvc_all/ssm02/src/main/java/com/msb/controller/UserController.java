package com.msb.controller;

import com.msb.pojo.User;
import com.msb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.action")
    public ModelAndView login(String uname,String password){
        ModelAndView mv=new ModelAndView();
        Map<String, Object> model = mv.getModel();
        model.put("msg", "脏话,你好");


        System.out.println("login.action");
        User user =userService.findUser(uname,password);
        if(null != user){
            mv.setViewName("/success.jsp");
        }else{
            mv.setViewName("/fail.jsp");

        }

        int i=1/0;

        return  mv;
    }
}
