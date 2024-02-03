package com.sean.io.test.rpc;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 17:56
 */
public class Dispatcher {

    private Dispatcher() {

    }

    private static Dispatcher dis = null;

    static {
        dis = new Dispatcher();
    }

    public static Dispatcher getDis() {
        return dis;
    }

    public static ConcurrentHashMap<String, Object> invokeMap = new ConcurrentHashMap<>();

    public void register(String k, Object obj) {
        invokeMap.put(k, obj);
    }

    public Object get(String k) {
        return invokeMap.get(k);
    }

}
