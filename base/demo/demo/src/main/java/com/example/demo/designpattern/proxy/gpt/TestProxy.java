package com.example.demo.designpattern.proxy.gpt;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;


/**
 * @author: kavanLi-R7000
 * @create: 2024-03-01 11:10
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestProxy {
    /**
     * 代理模式是一种设计模式，它允许一个对象代替另一个对象进行请求**。代理对象可以提供额外的功能，例如控制访问、增加性能或提供缓存。
     *
     * 代理模式的结构:
     *
     * Subject: 被代理的对象
     * Proxy: 代理对象
     * Client: 客户端
     * 代理模式的实现方式:
     *
     * 静态代理: 代理类和被代理类在编译时就确定
     * 动态代理: 代理类在运行时动态生成
     * 代理模式的应用场景:
     *
     * 远程代理: 在不同的地址空间中提供对象的代理
     * 虚拟代理: 根据需要创建开销很大的对象的代理
     * 安全代理: 控制对对象的访问权限
     * 智能指引: 在访问对象时执行附加操作
     * JDK动态代理
     * JDK动态代理是Java提供的一种动态代理实现方式。它使用Java反射机制在运行时动态生成代理类。
     *
     * JDK动态代理的实现原理:
     *
     * Proxy类: 提供创建代理对象的方法
     * InvocationHandler接口: 定义代理对象要拦截的方法
     */
    // 测试类
    public static void main(String[] args) {
        Tank tank = new Tank();
        LogAspect aspect = new LogAspect();

        Movable m = (Movable) Proxy.newProxyInstance(
                Tank.class.getClassLoader(),
                new Class[]{Movable.class},
                new AspectProxy(tank, aspect)
        );

        m.move();
    }
}

// 接口
interface Movable {
    void move();
}

// 被代理类
class Tank implements Movable {
    @Override
    public void move() {
        System.out.println("Tank moving claclacla...");
    }
}

// 切面
class LogAspect {
    public void before(String methodName, Object[] args) {
        System.out.println("method start: " + methodName + ", args: " + Arrays.toString(args));
    }

    public void after(String methodName, Object result) {
        System.out.println("method stop: " + methodName + ", result: " + result);
    }
}

// 代理类
class AspectProxy implements InvocationHandler {
    Movable m;
    LogAspect aspect;

    public AspectProxy(Movable m, LogAspect aspect) {
        this.m = m;
        this.aspect = aspect;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        aspect.before(methodName, args);
        Object result = method.invoke(m, args);
        aspect.after(methodName, result);
        return result;
    }
}