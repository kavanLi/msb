package com.mashibing.factory.abstract_factory;

import com.mashibing.factory.abstract_factory.factory.AppliancesFactory;
import com.mashibing.factory.abstract_factory.factory.HisenseFactory;
import com.mashibing.factory.abstract_factory.product.AbstractFreezer;
import com.mashibing.factory.abstract_factory.product.AbstractTV;

/**
 * 客户端
 * @author spikeCong
 * @date 2022/9/15
 **/
public class Client {

    private AbstractTV tv;

    private AbstractFreezer freezer;

    public Client(AppliancesFactory factory){

        //在客户端看来就是使用抽象工厂来生产家电
        this.tv = factory.createTV();
        this.freezer = factory.createFreezer();
    }

    public AbstractTV getTv() {
        return tv;
    }

    public void setTv(AbstractTV tv) {
        this.tv = tv;
    }

    public AbstractFreezer getFreezer() {
        return freezer;
    }

    public void setFreezer(AbstractFreezer freezer) {
        this.freezer = freezer;
    }

    public static void main(String[] args) {

        Client client = new Client(new HisenseFactory());
        AbstractTV tv = client.getTv();
        System.out.println(tv);

        AbstractFreezer freezer = client.getFreezer();
        System.out.println(freezer);
    }
}
