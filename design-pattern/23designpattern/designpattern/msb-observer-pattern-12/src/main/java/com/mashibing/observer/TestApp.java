package com.mashibing.observer;

import com.mashibing.observer.example02.LotteryResult;
import com.mashibing.observer.example03.LotteryService;
import com.mashibing.observer.example03.LotteryServiceImpl;

/**
 * @author spikeCong
 * @date 2022/10/12
 **/
public class TestApp {

    public static void main(String[] args) {

//        LotteryService lotteryService = new LotteryServiceImpl();
//        LotteryResult result = lotteryService.lottery("13579246810101010");
//        System.out.println(result);
        LotteryService ls = new LotteryServiceImpl();
        LotteryResult lotteryResult = ls.lotteryAndMsg("215673512673512736125763");
        System.out.println(lotteryResult);
    }
}
