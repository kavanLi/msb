package com.msb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
public class MyController {
    @ResponseBody
    @RequestMapping("/secondController")
    public String secondController(){
        return " hello springboot02";
    }
}

