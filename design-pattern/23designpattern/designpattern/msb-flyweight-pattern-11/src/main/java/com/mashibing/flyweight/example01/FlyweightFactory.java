package com.mashibing.flyweight.example01;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂类
 *      作用: 作为存储享元对象的享元池.用户获取享元对象时先从享元池中获取,有则返回,没有创建新的返回给用户,
 *      并且在享元池中保存
 * @author spikeCong
 * @date 2022/10/10
 **/
public class FlyweightFactory {

    //定义一个Map集合用于存储享元对象,实现享元池
    private Map<String,Flyweight> pool = new HashMap<>();

    //实现享元对象之间的状态传递
    public FlyweightFactory() {
        //添加对应的内部状态
        pool.put("A",new ConcreteFlyweight("A"));
        pool.put("B",new ConcreteFlyweight("B"));
        pool.put("C",new ConcreteFlyweight("C"));
    }

    //根据内部状态进行查找
    public Flyweight getFlyweight(String key){

        //对象是否存在
        if(pool.containsKey(key)){
            System.out.println("=== 享元池中存在,直接复用,key : " + key);
            return pool.get(key);
        }else{
            //如果对象不存在,就创建一个添加到享元池,然后返回
            System.out.println("=== 享元池中不存在,创建并复用,key: " + key);
            Flyweight fw = new ConcreteFlyweight(key);
            pool.put(key,fw);
            return  fw;
        }
    }

}
