package com.msb.core;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.msb.common.config.DynamicConfigManager;
import com.msb.common.config.ServiceDefinition;
import com.msb.common.config.ServiceInstance;
import com.msb.common.constants.BasicConst;
import com.msb.common.util.NetUtils;
import com.msb.common.util.TimeUtil;
import com.msb.core.config.Config;
import com.msb.core.config.ConfigLoader;
import com.msb.core.contain.Container;
import com.msb.gateway.config.center.api.ConfigCenter;
import com.msb.gateway.register.center.api.RegisterCenter;
import com.msb.gateway.register.center.api.RegisterCenterListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ServiceLoader;
import java.util.Set;
@Slf4j
public class GatewayBootstrap {
    public static void main(String[] args) {
        // 1、加载网关核心静态配置
        Config config = ConfigLoader.getInstance().load(args);
        // 2、插件初始化  netty组件
        // 3、配置中心管理器初始化，连接配置中心，监听个配置中心的新增，修改和删除
        initAndGetConfig(config);
        // 4、启动容器
        Container container = new Container(config);
        container.start();
        // 5、连接注册中心，将注册中心的实例加载到本地
        final RegisterCenter registerCenter = registerAndSubscribe(config);
        // 6、服务优雅的关机
        // 收到kill信号时调用
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                registerCenter.deRegister(buildGatewayServiceDefinition(config),
                        buildGatewayServiceInstance(config));
            }
        });
    }

    private static void initAndGetConfig(Config config) {
        ServiceLoader<ConfigCenter> configCenters = ServiceLoader.load(ConfigCenter.class);
        for (ConfigCenter configCenter : configCenters) {
            System.out.println(configCenter);
        }
        ConfigCenter configCenter = configCenters.iterator().next();
        configCenter.init(config.getRegistryAddress(), config.getEnv());
        configCenter.subscribeRuleChange(rules -> {
            DynamicConfigManager.getInstance().putAllRule(rules);
        });
    }

    private static RegisterCenter registerAndSubscribe(Config config) {
        ServiceLoader<RegisterCenter> serviceLoader = ServiceLoader.load(RegisterCenter.class);
        RegisterCenter registerCenter = serviceLoader.iterator().next();
        if(registerCenter == null){
            log.error("not found RegisterCenter impl");
            throw  new RuntimeException("not found RegisterCenter impl");
        }
        registerCenter.init(config.getRegistryAddress(),config.getEnv());

        // 构建网关的服务定义和服务实例
        ServiceDefinition serviceDefinition = buildGatewayServiceDefinition(config);
        ServiceInstance serviceInstance = buildGatewayServiceInstance(config);


        // 注册
        registerCenter.register(serviceDefinition,serviceInstance);
        // 订阅,发现变化则将其放到DynamicConfigManager中
        registerCenter.subscribeAllServices(new RegisterCenterListener() {
            @Override
            public void onChange(ServiceDefinition serviceDefinition, Set<ServiceInstance> serviceInstanceSet) {
                log.info("refresh service and instance :{} {}",serviceDefinition.getUniqueId(),
                        JSON.toJSON(serviceInstanceSet));
                DynamicConfigManager manager = DynamicConfigManager.getInstance();
                manager.addServiceInstance(serviceDefinition.getUniqueId(),serviceInstanceSet);
            }
        });
        return registerCenter;
    }

    /**
     * 构建服务定义信息
     * @param config
     * @return
     */
    private static ServiceDefinition buildGatewayServiceDefinition(Config config) {
        ServiceDefinition serviceDefinition = new ServiceDefinition();
        serviceDefinition.setInvokerMap(ImmutableMap.of());
        serviceDefinition.setUniqueId(config.getApplicationName());
        serviceDefinition.setServiceId(config.getApplicationName());
        serviceDefinition.setEnvType(config.getEnv());
        return serviceDefinition;
    }

    /**
     * 构建服务实例信息
     * @param config
     * @return
     */
    private static ServiceInstance buildGatewayServiceInstance(Config config) {
        // 获取地址ip
        String localIp = NetUtils.getLocalIp();
        int port = config.getPort();
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setServiceInstanceId(localIp + BasicConst.COLON_SEPARATOR + port);
        serviceInstance.setIp(localIp);
        serviceInstance.setPort(port);
        serviceInstance.setRegisterTime(TimeUtil.currentTimeMillis());
        return serviceInstance;
    }
}
