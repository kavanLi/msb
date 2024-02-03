package com.msb.gateway.register.center.api;

import com.msb.common.config.ServiceDefinition;
import com.msb.common.config.ServiceInstance;

import java.util.Set;

public interface RegisterCenterListener {

    void onChange(ServiceDefinition serviceDefinition,
                  Set<ServiceInstance> serviceInstanceSet);
}
