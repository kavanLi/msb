package com.msb.core;

public class GatewayBootstrap {
    public static void main(String[] args) {
        // 1、加载网关的核心配置
        // 2、组件的初始化 netty
        // 3、配置中心管理器初始化，连接配置中心，监听配置中心的新增、修改和删除
        // 4、启动容器
        // 5、连接注册中心，将注册中心的实例加载到本地
        // 6、服务优雅关机
    }
}
