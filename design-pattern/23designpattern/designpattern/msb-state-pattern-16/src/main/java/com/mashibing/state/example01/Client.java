package com.mashibing.state.example01;

/**
 * @author spikeCong
 * @date 2022/10/17
 **/
public class Client {

    public static void main(String[] args) {

        Context context = new Context();

        State state1 = new ConcreteStateA();
        state1.handle(context);
        System.out.println(context.getCurrentState().toString());

        System.out.println("=================================");
        State state2 = new ConcreteStateB();
        state2.handle(context);
        System.out.println(context.getCurrentState().toString());
    }
}
