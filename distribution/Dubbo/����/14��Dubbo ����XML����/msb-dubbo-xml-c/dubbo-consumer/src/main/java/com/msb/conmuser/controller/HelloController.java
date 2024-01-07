package com.msb.conmuser.controller;

import com.msb.service.HelloServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloServiceAPI helloServiceAPI;

    @GetMapping("/sayHello")
    public String sayHello(){
        return helloServiceAPI.sayHello("hello everyBoyd");
    }
}
