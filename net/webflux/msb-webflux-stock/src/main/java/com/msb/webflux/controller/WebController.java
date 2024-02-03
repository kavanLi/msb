package com.msb.webflux.controller;

import com.msb.webflux.Constants;
import com.msb.webflux.dto.StockPrice;
import com.msb.webflux.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

@Controller
public class WebController {

    @Autowired
    private StockPriceService stockPriceService;

    @RequestMapping("/")
    public String index(Model model){
        Flux<StockPrice> price = stockPriceService.getPrice(Constants.TEST_USER_EMAIL);
        model.addAttribute("email",Constants.TEST_USER_EMAIL);
        model.addAttribute("stockPrices",new ReactiveDataDriverContextVariable(price));
        return "index";
    }


}
