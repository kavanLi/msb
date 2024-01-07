package com.msb.controller;

import com.msb.pojo.Person;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

@RestController
public class TestAnno {

    @RequestMapping("/testAnno1")
    public String getParam(Person p ,@RequestParam(value = "pname") String name, @RequestParam("page") Integer age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    @RequestMapping("/testAnno2")
    public String getHeaders(@RequestHeader("Accept") String accept){
        System.out.println(accept);
        return "success";
    }

    @RequestMapping("/testAnno3")
    public String getCookie(@CookieValue(value = "JSESSIONID") String jsessionid){
        System.out.println(jsessionid);
        return "success";
    }
}
