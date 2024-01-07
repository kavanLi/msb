package com.msb.dubbo.consumer.controller;

import com.msb.dubbo.consumer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/createOrder/{userId}")
    public String createOrder(@PathVariable("userId") Long userId){
            return orderService.createOrder(userId);
    }
}
