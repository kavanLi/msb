package com.mashibing.proxy.example04;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author spikeCong
 * @date 2022/11/20
 **/
public class ProxyFactory {
    // 代理对象
    private Object target;

    public ProxyFactory(Object t) {
        target = t;
    }

    // 为目标对象生成代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),     // 目标类使用的类加载器
                target.getClass().getInterfaces(),      // 目标类实现的接口
                new InvocationHandler() {               // 事件处理器匿名实现类
                    /**
                     * @param proxy 代理对象
                     * @param method 代理对象的方法
                     * @param args 方法参数
                     * @return 代理对象方法的返回值，没有就返回null
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        before();
                        Object result = method.invoke(target, args);
                        after();
                         return result;
                    }
                }
        );
    }

    private void before() {
        System.out.println("before method...");
    }
    private void after() {
        System.out.println("after method...");
    }

}
