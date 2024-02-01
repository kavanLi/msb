package com.mashibing.interpreter.example01;

/**
 * @author spikeCong
 * @date 2022/10/21
 **/
public class Client {

    public static void main(String[] args) {

        ExpressionInterpreter interpreter = new ExpressionInterpreter();
        long result = interpreter.interpret("6 8 3 2 4 + - + *");
        System.out.println("6 8 3 2 4 + - + * 表达式的结果为: "+result);
    }
}
