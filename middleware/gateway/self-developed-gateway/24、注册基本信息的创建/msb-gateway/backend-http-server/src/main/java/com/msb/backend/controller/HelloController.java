package com.msb.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/http-demo/ping")
    public String ping(){
        return "pong";
    }
}
