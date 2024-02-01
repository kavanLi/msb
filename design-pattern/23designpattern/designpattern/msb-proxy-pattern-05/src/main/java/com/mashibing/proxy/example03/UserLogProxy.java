package com.mashibing.proxy.example03;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author spikeCong
 * @date 2022/9/23
 **/
public class UserLogProxy implements MethodInterceptor {


    /**
     * 生成CGLIB动态代理类方法
     * @param target    需要被代理的目标类
     * @return: java.lang.Object  代理类对象
     */
    public Object getLogProxy(Object target){

        //增强器类,用来创建动态代理类
        Enhancer enhancer = new Enhancer();

        //设置代理类的父类字节码对象
        enhancer.setSuperclass(target.getClass());

        //设置回调
        enhancer.setCallback(this);

        //创建动态代理对象,并返回
        return enhancer.create();
    }

    /**
     * 实现回调方法
     * @param o      代理对象
     * @param method  目标对象中的方法的Method实例
     * @param args      实际参数
     * @param methodProxy  代理类对象中的方法的Method实例
     * @return: java.lang.Object
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        Calendar instance = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(format.format(instance.getTime()) + "[ " +method.getName() +"] 查询用户信息...");
        Object result = methodProxy.invokeSuper(o, args);
        return null;
    }
}
