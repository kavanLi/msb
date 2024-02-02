package com.mashibing.strategy.example01;

/**
 * @author spikeCong
 * @date 2022/10/13
 **/
public class Client {

    public static void main(String[] args) {

        Strategy strategyB= new ConcreteStrategyB();
        Context context = new Context(strategyB);

        context.algorithm();
    }
}
