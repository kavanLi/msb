package com.mashibing.visitor.example01;

import java.time.LocalDate;

/**
 * 酒水类
 * @author spikeCong
 * @date 2022/10/18
 **/
public class Wine extends Product implements Acceptable{

    public Wine(String name, LocalDate produceDate, double price) {
        super(name, produceDate, price);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
