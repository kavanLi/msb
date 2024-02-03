package com.msb.lambda;

import java.util.function.*;

class Dog{
    private String name= "哮天犬";
    private int food =10;

    public static void bark(Dog dog){
        System.out.println(dog +"叫了");
    }

    public Dog (String name){
        this.name = name;
    }
    public Dog ( ){

    }
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * 吃狗粮，静态方法
     * 接下来我们对这成员方法进行成员引用，成员引用套路都是一样的，就是分析他的输入和输出，输入一个int输出一个int
     * @param this  JDK默认会把当前实例传入到非静态方法，参数名为this,位置是第一个
     * @param num
     * @return
     */

    public int eat(Dog this,int num){
        System.out.println("吃了" +num+ "斤狗粮");
        this.food = this.food - num;
        return this.food;
    }
}
public class MethodRefrenceDemo {
    public static void main(String[] args) {
        // 这就是一个lambda表达式，箭头左边是输入参数，箭头右边是函数的执行体
        // 当你的函数执行体里面只有一个函数进行调用，而且函数的参数和传入参数是一样的话，我们就缩写成
        // 方法引用的方式
       Consumer<String> consumer = s-> System.out.println(s);
        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("接受数据");
       //静态方法的方法引用
        Dog dog = new Dog();
        Consumer<Dog> consumer3 = Dog::bark;
        consumer3.accept(dog);

        // 非静态方法，使用对象实例来引用
        Function<Integer,Integer> funtion = dog::eat;
        System.out.println("还剩下" +funtion.apply(2)+ "斤");
        // 演进 1 当输入和输入都是相同类型则可以用一元函数
        UnaryOperator<Integer> unaryOperator = dog::eat;
        System.out.println("还剩下" +unaryOperator.apply(2)+ "斤");
        // 演进2 :因为我们这都是Integer,所以JDK8针对类型也有对应的函数接口
        IntUnaryOperator intUnaryOperator = dog::eat;
        System.out.println("还剩下" +intUnaryOperator.applyAsInt(2)+ "斤");

        // 静态方法和成员方法有一个区别是什么呢？
        // 成员方法可以调用this, 静态方法不能调用this ，那jdk是怎样实现的的，他是在方法里面传递了this

        Dog dog1 = new  Dog();
        dog1.eat(23);

        // 使用类名来方法引用
        BiFunction<Dog,Integer,Integer> eatFunction = Dog::eat;
        System.out.println(eatFunction.apply(dog1,2));

        // 构造函数的方法引用
        // 无构造参数
        // 我们dog里面没有对应的构造方法，我们默认是空的构造方法
        // 这也就是传入是空的，返回一个结果也就生产者
        Supplier<Dog> supplier  = Dog::new;
        System.out.println("创建了新对象：" +supplier.get());
        // 有参数的构造方法
        // 我们这里是和上面一样的，我们的JDK会自动找到有对应参数的
        Function<String,Dog> fun = Dog::new;
        System.out.println(fun.apply("警犬"));


    }
}
