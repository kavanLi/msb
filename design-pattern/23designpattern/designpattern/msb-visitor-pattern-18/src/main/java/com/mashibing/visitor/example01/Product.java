package com.mashibing.visitor.example01;

import java.time.LocalDate;

/**
 * 抽象商品父类
 * @author spikeCong
 * @date 2022/10/18
 **/
public abstract class Product {

    private String name; //商品名

    private LocalDate produceDate; //生产日期

    private double price; //商品价格

    public Product(String name, LocalDate produceDate, double price) {
        this.name = name;
        this.produceDate = produceDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(LocalDate produceDate) {
        this.produceDate = produceDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
