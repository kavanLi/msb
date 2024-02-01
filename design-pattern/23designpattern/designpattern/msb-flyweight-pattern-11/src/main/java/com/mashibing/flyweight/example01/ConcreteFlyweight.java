package com.mashibing.flyweight.example01;

/**
 * 可共享的-具体享元类
 * 注意: 在具体享元类中,需要将内部状态和外部状态分开处理.
 * @author spikeCong
 * @date 2022/10/10
 **/
public class ConcreteFlyweight extends Flyweight{

    //内部状态 : inState作为一个成员变量,同一个享元对象的内部状态是一致的.
    private String inState;

    public ConcreteFlyweight(String inState) {
        this.inState = inState;
    }


    /**
     * 外部状态在使用的时候,通常是有外部设置,不保存在享元对象中,即使是同一个对象
     * @param state
     */
    @Override
    public void operation(String state) {

        System.out.println("=== 享元对象的内部状态: " + inState + ",外部状态: " + state );
    }
}
