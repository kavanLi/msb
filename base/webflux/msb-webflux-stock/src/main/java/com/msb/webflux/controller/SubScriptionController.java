package com.msb.webflux.controller;

import com.msb.webflux.Constants;
import com.msb.webflux.model.StockSubsciption;
import com.msb.webflux.model.StockSymbol;
import com.msb.webflux.service.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/subscriptions")
public class SubScriptionController {
    @Autowired
    private SubscriptionsService subscriptionsService;

    @PostMapping
    public String addSubscription(@ModelAttribute(value = "stockSymbol")StockSymbol symbol){
        String email = Constants.TEST_USER_EMAIL;
        subscriptionsService.addSubscription(email,symbol.getSymbol()).subscribe();
        return "redirect:/subscriptions?added=" + symbol.getSymbol();
    }

    @GetMapping
    public String subscription(Model model){
        // 获取订阅的股票
        Flux<StockSubsciption> subscriptions = subscriptionsService.findByEmail(Constants.TEST_USER_EMAIL);
        model.addAttribute("email",Constants.TEST_USER_EMAIL);
        model.addAttribute("subscriptions",new ReactiveDataDriverContextVariable(subscriptions));
        return "subscription";
    }

}
