package com.example.demo.designpattern.intepreter.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-04 15:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestIntepreter {
/**
 *
 解释器模式简介
 定义:

 解释器模式是一种设计模式，它给定一种语言，定义它的文法的一种表示，并定义一个解释器，这个解释器使用该表示来解释语言中的句子。

 特点:

 将语言的文法和解释器分离，提高代码的可维护性和灵活性。
 可以方便地扩展新的语法规则。
 提高代码的执行效率，因为解释器只需要解释一次语法规则，就可以多次使用。
 应用场景:

 编译器
 计算器
 正则表达式
 XML 解析
 代码案例:

 示例：计算器

 假设我们有一个简单计算器，需要支持加减乘除运算。我们可以使用解释器模式来实现这个功能。
 */
    public static void main(String[] args) {
        Expression expression = new PlusExpression(
                new NumberExpression(1),
                new MultiplyExpression(
                        new NumberExpression(2),
                        new NumberExpression(3)
                )
        );

        Interpreter interpreter = new Interpreter(expression);
        int result = interpreter.interpret();
        System.out.println("计算结果：" + result);
    }

}

interface Expression {
    int interpret();
}

class NumberExpression implements Expression {

    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    @Override
    public int interpret() {
        return number;
    }
}

class PlusExpression implements Expression {

    private Expression left;
    private Expression right;

    public PlusExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() + right.interpret();
    }
}

class MinusExpression implements Expression {

    private Expression left;
    private Expression right;

    public MinusExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() - right.interpret();
    }
}

class MultiplyExpression implements Expression {

    private Expression left;
    private Expression right;

    public MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() * right.interpret();
    }
}

class DivideExpression implements Expression {

    private Expression left;
    private Expression right;

    public DivideExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() / right.interpret();
    }
}

class Interpreter {

    private Expression expression;

    public Interpreter(Expression expression) {
        this.expression = expression;
    }

    public int interpret() {
        return expression.interpret();
    }
}
