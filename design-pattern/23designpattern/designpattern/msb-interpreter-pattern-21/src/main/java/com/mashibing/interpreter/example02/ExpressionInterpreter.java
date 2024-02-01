package com.mashibing.interpreter.example02;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 表达式解释器类
 * @author spikeCong
 * @date 2022/10/21
 **/
public class ExpressionInterpreter {

    private Deque<Expression> numbers = new LinkedList<>();

    public long interpret(String expression){

        String[] elements = expression.split(" ");

        int length = elements.length;

        for (int i = 0; i < (length + 1) /2; ++i) {
            numbers.addLast(new NumExpression(elements[i]));
        }

        for (int i = (length + 1) / 2; i < length; ++i) {

            String operator = elements[i];

            //符号必须是 + - * / ,否则抛出异常
            boolean isValid = "+".equals(operator) || "-".equals(operator) ||
                    "*".equals(operator) || "/".equals(operator);

            if(!isValid){
                throw new RuntimeException("无效表达式!" + expression);
            }

            Expression exp1 = numbers.pollFirst();
            Expression exp2 = numbers.pollFirst();

            Expression result = null;
            if("+".equals(operator)){
                result = new PluExpression(exp1,exp2);
            }else if("-".equals(operator)){
                result = new SubExpression(exp1,exp2);
            }else if("*".equals(operator)){
                result = new MulExpression(exp1,exp2);
            }else if("/".equals(operator)){
                result = new DivExpression(exp1,exp2);
            }

            long num = result.interpret();
            numbers.addFirst(new NumExpression(num));
        }

        if(numbers.size() != 1){
            throw new RuntimeException("无效的表达式!" + expression);
        }

        return numbers.pop().interpret();
    }

}
