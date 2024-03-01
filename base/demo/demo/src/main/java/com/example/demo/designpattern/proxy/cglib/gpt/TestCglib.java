package com.example.demo.designpattern.proxy.cglib.gpt;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-01 10:30
 * To change this template use File | Settings | File and Code Templates.
 */
// 测试类
public class TestCglib {

    /**
     Cglib的底层原理是ASM:

     Cglib使用ASM字节码生成框架，动态生成目标类的子类，并在子类中插入自定义的逻辑。

     JDK动态代理的底层原理是Java反射:

     JDK动态代理使用Java反射机制，在运行时动态生成代理类，并使用InvocationHandler接口来实现代理逻辑。

     Cglib和JDK动态代理的比较:

     特性	Cglib	JDK动态代理
     底层原理	ASM	Java反射
     性能	较高	较低
     侵入性	强	弱
     适用范围	广泛	仅限于实现了接口的类

     如果需要对目标类进行更复杂的控制，可以选择Cglib。
     如果只需要简单的代理功能，可以选择JDK动态代理。

     *
     * Cglib是一个强大的字节码生成库，可以动态生成Java类。Cglib可以用于实现设计模式，例如代理模式、工厂模式、装饰模式等。
     *
     * Cglib实现设计模式的原理:
     *
     * Cglib可以动态生成一个子类，并重写父类的方法。
     * Cglib可以在子类中插入自定义的逻辑。
     * Cglib实现设计模式的优点:
     *
     * 灵活: 可以根据需要动态生成类，从而使代码更加灵活。
     * 高效: Cglib生成的类可以直接执行，无需解释，因此效率较高。
     * Cglib实现设计模式的缺点:
     *
     * 复杂: Cglib的API比较复杂，使用起来有一定的难度。
     * 侵入性: Cglib需要修改目标类的字节码，因此具有一定的侵入性。
     * @param args
     */
    public static void main(String[] args) {
        // 创建目标类对象
        Target target = new Target();

        // 创建Cglib代理对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                // 在目标类方法执行前后添加自定义逻辑
                System.out.println("代理类开始执行");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("代理类执行结束");
                return result;
            }
        });
        Target proxy = (Target) enhancer.create();

        // 调用代理类的方法
        proxy.doSomething();
    }
}

// 目标类
class Target {
    public void doSomething() {
        System.out.println("目标类执行doSomething方法");
    }
}

// 代理类
class Proxy {
    public void doSomething() {
        System.out.println("代理类执行doSomething方法");
        // 调用目标类的方法
        new Target().doSomething();
    }
}