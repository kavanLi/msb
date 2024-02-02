package com.mashibing.singleton.demo06;

/**
 * 单例模式-枚举
 *      阻止反射的破坏: 在反射方法中不允许使用反射创建枚举对象
 *      阻止序列化的破坏: 在序列化的时候仅仅是将枚举对象的name属性输出到了结果中,反序列化的时候,就会通过
 *      Enum的 valueOf方法 来根据名字去查找对应枚举对象.
 * @author spikeCong
 * @date 2022/9/6
 **/
public enum Singleton_06 {

    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Singleton_06 getInstance(){

        return INSTANCE;
    }
}
