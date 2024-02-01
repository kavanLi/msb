package com.mashibing.interpreter.example01;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 表达式解释器
 * @author spikeCong
 * @date 2022/10/21
 **/
public class ExpressionInterpreter {

    //Deque双向队列 ,可以从队列两端增加或删除元素
    private Deque<Long> numbers = new LinkedList<Long>();

    //接收表达式进行解析
    public long interpret(String expression){

        //9 5 7 3 - + *
        String[] elements = expression.split(" ");

        int length = elements.length;

        //获取表达式中的数字
        for (int i = 0; i < (length+1) / 2; ++i) {
            //向集合的尾部添加元素
            numbers.addLast(Long.parseLong(elements[i]));
        }

        //获取表达式中的符号,进行计算
        for (int i = (length+1) / 2; i < length; ++i) {
            String operator = elements[i];
            //符号必须是 + - * / ,否则抛出异常
            boolean isValid = "+".equals(operator) || "-".equals(operator) ||
                    "*".equals(operator) || "/".equals(operator);

            if(!isValid){
                throw new RuntimeException("无效表达式!" + expression);
            }

            //获取集合中的数字,移除集合中的第一个元素,并且返回被移除的值
            long number1 = numbers.pollFirst();  //数字
            long number2 = numbers.pollFirst();  //数字

            long result = 0;
            if("+".equals(operator)){
                result = number1 + number2;
            }else if("-".equals(operator)){
                result = number1 - number2;
            }else if("*".equals(operator)){
                result = number1 * number2;
            }else if("/".equals(operator)){
                result = number1 / number2;
            }

            //将运算结果添加到集合头部
            numbers.addFirst(result);
        }

        //运算的最终结果是被保存在集合中的
        if(numbers.size() != 1){
            throw new RuntimeException("无效表达式!" + expression);
        }

        //移除集合中的唯一的结果,并返回
        return numbers.pop();
    }
}
