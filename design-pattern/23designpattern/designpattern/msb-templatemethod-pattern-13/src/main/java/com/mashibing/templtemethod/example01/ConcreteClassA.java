package com.mashibing.templtemethod.example01;

/**
 * @author spikeCong
 * @date 2022/10/12
 **/
public class ConcreteClassA extends AbstractClassTemplate {

    @Override
    void step3() {
        System.out.println("在子类A中 -> 执行步骤3");
    }

    @Override
    void step4() {
        System.out.println("在子类A中 -> 执行步骤4");
    }
}
