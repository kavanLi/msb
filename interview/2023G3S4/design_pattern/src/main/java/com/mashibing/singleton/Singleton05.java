package com.mashibing.singleton;

/**
 * 枚举类
 * 枚举类型是线程安全的，并且只会装载一次
 * @author spikeCong
 * @date 2023/3/8
 **/
public enum Singleton05{

    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Singleton05 getInstance(){

        return INSTANCE;
    }
}