package com.mashibing.proxy.demo02;

import com.mashibing.proxy.demo01.IUserDao;
import com.mashibing.proxy.demo01.UserDaoImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author spikeCong
 * @date 2023/3/19
 **/
public class ProxyFactory {

    private Object target; //维护一个目标对象

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //为目标对象生成代理对象
    public Object getProxyInstance(){

        //使用Proxy获取代理对象
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(), //目标类使用的类加载器
                target.getClass().getInterfaces(), //目标对象实现的接口类型
                new InvocationHandler(){ //事件处理器

                    /**
                     * invoke方法参数说明
                     * @param proxy 代理对象
                     * @param method 对应于在代理对象上调用的接口方法Method实例
                     * @param args 代理对象调用接口方法时传递的实际参数
                     * @return: java.lang.Object 返回目标对象方法的返回值,没有返回值就返回null
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开启事务");

                        //执行目标对象方法
                        method.invoke(target, args);
                        System.out.println("提交事务");
                        return null;
                    }
                }
        );
    }

    //测试
    public static void main(String[] args) {
        IUserDao target = new UserDaoImpl();
        System.out.println(target.getClass());//目标对象信息

        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass()); //输出代理对象信息
        proxy.save(); //执行代理方法
    }
}
