package com.msb.lambda;

interface Interface1{
    int doubleNum(int i);
    default int add(int x,int y){
        return x + y;
    }
}
public class LambdaDemo1 {
    public static void main(String[] args) {

        Interface1 i1 = (i) -> i*2;
        i1.add(3,7);
        i1.doubleNum(12);

        //这是最常用的一种方式
        Interface1 i2 = i -> i *2;

        Interface1 i3 =(int i) ->  i *2;

        Interface1 i4 = (int i) ->{
            System.out.println("lambda 表达式使用");
            return i *2;
        };

    }
}
