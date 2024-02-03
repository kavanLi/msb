package com.msb.gateway.register.center.api;


import com.msb.common.config.ServiceDefinition;
import com.msb.common.config.ServiceInstance;

public interface RegisterCenter {
    /**
     * 初始化
     * @param registerAddress
     * @param env
     */
    void init(String registerAddress,String env);

    /**
     * 注册
     */
    void register(ServiceDefinition serviceDefinition, ServiceInstance serviceInstance);

    /**
     * 注销
     * @param serviceDefinition
     * @param serviceInstance
     */
    void deRegister(ServiceDefinition serviceDefinition,ServiceInstance serviceInstance);

    /**
     * 订阅所有的变更服务
     * @param registerCenterListener
     */
    void subscribeAllServices(RegisterCenterListener registerCenterListener);
}
