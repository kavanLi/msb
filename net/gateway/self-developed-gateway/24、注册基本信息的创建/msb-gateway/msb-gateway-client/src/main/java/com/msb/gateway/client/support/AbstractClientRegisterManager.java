package com.msb.gateway.client.support;

import com.msb.common.config.ServiceDefinition;
import com.msb.common.config.ServiceInstance;
import com.msb.gateway.client.core.ApiProperties;
import com.msb.gateway.register.center.api.RegisterCenter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ServiceLoader;

@Slf4j
public class AbstractClientRegisterManager {
    @Getter
    private ApiProperties apiProperties;

    private RegisterCenter registerCenter;

    protected AbstractClientRegisterManager(ApiProperties apiProperties){
        this.apiProperties = apiProperties;

        // 利用Java的SPI初始化注册中心对象
        ServiceLoader<RegisterCenter> serviceLoader = ServiceLoader.load(RegisterCenter.class);
        registerCenter = serviceLoader.iterator().next();
        //初始化
        registerCenter.init(apiProperties.getRegisterAddress(),apiProperties.getEnv());
    }

    protected void register(ServiceDefinition serviceDefinition,
                            ServiceInstance serviceInstance){
        registerCenter.register(serviceDefinition,serviceInstance);

    }
}
