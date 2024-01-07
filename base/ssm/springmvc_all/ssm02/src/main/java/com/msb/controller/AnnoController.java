package com.msb.controller;

import com.msb.pojo.Emp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
@CrossOrigin(origins = "http://domin.com", maxAge = 3600)
public class AnnoController {
    @RequestMapping("demo1.action")
    @ResponseBody
    public Emp demo1(@RequestBody(required = false) Emp emp){
        System.out.println(emp);
        return emp;
    }

}
