package com.mashibing.visitor.example01;


import java.text.NumberFormat;
import java.time.LocalDate;

/**
 * 折扣计价访问者类
 * @author spikeCong
 * @date 2022/10/18
 **/
public class DiscountVisitor implements Visitor {

    private LocalDate billDate;

    public DiscountVisitor(LocalDate billDate) {
        this.billDate = billDate;
        System.out.println("结算日期: " + billDate);
    }

    @Override
    public void visit(Candy candy) {
        System.out.println("糖果: " + candy.getName());

        //糖果大于180天,禁止售卖,否则糖果一律九折
        long days = billDate.toEpochDay() - candy.getProduceDate().toEpochDay();

        if(days > 180){
            System.out.println("超过半年的糖果,请勿食用!");
        }else{
            double realPrice = candy.getPrice() * 0.9;
            System.out.println("糖果打折后的价格为: " +
                    NumberFormat.getCurrencyInstance().format(realPrice));
        }
    }

    @Override
    public void visit(Wine wine) {
        System.out.println("酒类: " + wine.getName() +",无折扣价格!");
        System.out.println("原价售卖: " +
                NumberFormat.getCurrencyInstance().format(wine.getPrice()));
    }

    @Override
    public void visit(Fruit fruit) {
        System.out.println("水果: " + fruit.getName());

        long days = billDate.toEpochDay() - fruit.getProduceDate().toEpochDay();

        double rate = 0;
        if(days > 7){
            System.out.println("超过七天的水果,请勿食用!");
        }else if(days > 3){
            rate = 0.5;
        }else{
            rate = 1;
        }

        double realPrice = fruit.getPrice() * fruit.getWeight() * rate;
        System.out.println("水果价格为: " +
                NumberFormat.getCurrencyInstance().format(realPrice));
    }
}
