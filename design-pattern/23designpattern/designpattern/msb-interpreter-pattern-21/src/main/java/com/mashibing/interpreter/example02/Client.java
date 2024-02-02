package com.mashibing.interpreter.example02;

/**
 * @author spikeCong
 * @date 2022/10/21
 **/
public class Client {

    public static void main(String[] args) {

        ExpressionInterpreter e = new ExpressionInterpreter();
        long result = e.interpret("10 2 5 5 5 / - + *");
        System.out.println(result);
    }
}
