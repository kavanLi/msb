package com.mashibing.proxy.example02;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂类-动态的生成代理对象
 * @author spikeCong
 * @date 2022/9/22
 **/
public class ProxyFactory {

    //维护一个目标对象
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //为目标对象生成代理对象
    public Object getProxyInstance(){

        return Proxy.newProxyInstance(
                //目标类使用的类加载器
                target.getClass().getClassLoader(),
                //目标对象实现的接口类型
                target.getClass().getInterfaces(),
                new InvocationHandler() { //事件处理器

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        System.out.println("开启事务");
                        method.invoke(target,args);
                        System.out.println("提交事务");
                        return null;
                    }
                }
        );
    }

}
