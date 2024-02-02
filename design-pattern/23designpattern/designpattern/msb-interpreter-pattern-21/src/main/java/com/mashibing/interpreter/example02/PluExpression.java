package com.mashibing.interpreter.example02;

/**
 * 加法运算
 * @author spikeCong
 * @date 2022/10/21
 **/
public class PluExpression implements Expression {

    private Expression exp1;

    private Expression exp2;

    public PluExpression(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public long interpret() {
        return exp1.interpret() + exp2.interpret();
    }
}
