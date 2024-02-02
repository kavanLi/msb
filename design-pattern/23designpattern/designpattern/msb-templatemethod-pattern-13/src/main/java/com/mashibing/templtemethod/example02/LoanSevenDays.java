package com.mashibing.templtemethod.example02;

/**
 * 借款七天
 * @author spikeCong
 * @date 2022/10/12
 **/
public class LoanSevenDays extends Account{

    @Override
    public void calculate() {
        System.out.println("借款周期7天,无利息! 仅收取贷款金额的1%的服务费");
    }

    @Override
    public void display() {
        System.out.println("七日借款内无利息!");
    }
}
