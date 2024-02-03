package com.msb.lambda;

import java.lang.reflect.Type;

@FunctionalInterface
interface IMath{
    int add(int x,int y);
}

@FunctionalInterface
interface IMath2{
    int add(int x,int y);
}

public class TypeDemo {
    public static void main(String[] args) {
       // 变量类型定义
       IMath lambda = (x,y) -> x +y;
       // 强转
        Object lambda2 = (IMath) (x,y) -> x + y;
        //通过返回类型
        IMath createLambda = createLambda();
        // 实际我们编写的是后都是在方法里面调用的
        TypeDemo demo = new TypeDemo();
       // demo.test((x,y) ->x +y);
        // 注意：我们这里如果重载的话就会有问题，此时我们可以
        //通过强制类型转化
        demo.test((IMath) (x,y) ->x +y);
    }

    public void test(IMath iMath){

    }
    public void test(IMath2 iMath){

    }
    private static IMath createLambda() {
        return  (x,y) -> x +y;
    }
}
