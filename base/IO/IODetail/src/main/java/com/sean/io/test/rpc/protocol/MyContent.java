package com.sean.io.test.rpc.protocol;

import java.io.Serializable;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 17:40
 */
public class MyContent implements Serializable {

    //请求类型
    String name;
    String methodName;
    Class<?>[] parameterTypes;
    Object[] args;

    //返回类型
    Object res;

    public Object getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}


