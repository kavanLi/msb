package com.sean.io.test.proxy;


import com.sean.io.test.rpc.protocol.MyContent;
import com.sean.io.test.rpc.Dispatcher;
import com.sean.io.test.rpc.transport.ClientFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;


/**
 * @author: 马士兵教育
 * @create: 2020-08-16 17:32
 */
public class MyProxy {
    static Dispatcher dis = Dispatcher.getDis();

    public static <T> T proxyGet(Class<T> interfaceInfo) {
        //实现各个版本的动态代理。。。。

        ClassLoader loader = interfaceInfo.getClassLoader();
        Class<?>[] methodInfo = {interfaceInfo};


        Object o = dis.get(interfaceInfo.getName());

        return (T) Proxy.newProxyInstance(loader, methodInfo, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //如何设计我们的consumer对于provider的调用过程

                //1，调用 服务，方法，参数  ==》 封装成message  [content]


                //TODO 有了content 其实可以到触发2个流程了 1 local调用 2 远程调用，但是需要这样一部代理，因为这里可以附加其他加工流程，监控等等

                Object o = dis.get(interfaceInfo.getName());
                Object res = null;

                if (o == null) {
                    String name = interfaceInfo.getName();
                    String methodName = method.getName();
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    MyContent content = new MyContent();
                    content.setArgs(args);
                    content.setName(name);
                    content.setMethodName(methodName);
                    content.setParameterTypes(parameterTypes);


                    //TODO 这里是rpc底层的流程   牵扯到 1，注册发现 ；2，provider的负载均衡；3，IO的负载均衡；
                    /**
                     * serviceA:ipA:port
                     *              socket1
                     *              socket2
                     * serviceA:ipB:port
                     * serviceA:ipC:port
                     */

                    CompletableFuture resF = ClientFactory.transport(content);

                    res = resF.get();//阻塞的

                } else {
                    //local调用了，那么不需要远程调用
                    Class<?> clazz = o.getClass();

                    try {
                        //做一些度量统计的插件
                        Method m = clazz.getMethod(method.getName(), method.getParameterTypes());
                        res = m.invoke(o, args);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }


                return res;

            }
        });


    }

}
