package com.msb.webflux.controller;

import com.msb.webflux.model.Stock;
import com.msb.webflux.model.StockSymbol;
import com.msb.webflux.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    private String getStocks(Model model){
        Flux<Stock> stocks = stockService.getAllStocks();
        model.addAttribute("stocks",new ReactiveDataDriverContextVariable(stocks));
        model.addAttribute("stockSymbol",new StockSymbol());
        return "stocks";
    }
}
