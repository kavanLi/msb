package com.mashibing.templtemethod.example01;

/**
 * @author spikeCong
 * @date 2022/10/12
 **/
public class Test01 {

    public static void main(String[] args) {

        AbstractClassTemplate concreteClassA = new ConcreteClassA();
        concreteClassA.run("x");

        System.out.println("================");

        AbstractClassTemplate concreteClassB = new ConcreteClassB();
        concreteClassB.run("");


    }
}
