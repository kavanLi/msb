package com.mashibing.visitor.example01;

import java.time.LocalDate;

/**
 * 糖果类
 * @author spikeCong
 * @date 2022/10/18
 **/
public class Candy extends Product implements Acceptable{

    public Candy(String name, LocalDate produceDate, double price) {
        super(name, produceDate, price);
    }

    @Override
    public void accept(Visitor visitor) {
        //在accept方法中调用访问者, 并将自己 this 传递回去.
        visitor.visit(this);
    }
}
