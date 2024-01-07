package com.msb.gateway.register.center.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingMaintainFactory;
import com.alibaba.nacos.api.naming.NamingMaintainService;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.Service;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.alibaba.nacos.common.executor.NameThreadFactory;
import com.google.common.collect.ImmutableMap;
import com.msb.common.config.ServiceDefinition;
import com.msb.common.config.ServiceInstance;
import com.msb.common.constants.GatewayConst;
import com.msb.gateway.register.center.api.RegisterCenter;
import com.msb.gateway.register.center.api.RegisterCenterListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class NacosRegisterCenter implements RegisterCenter {
    //注册地址
    private String registerAddress;
    // 环境
    private String env;
    // 主要用于维护服务实例信息
    private NamingService namingService;
    // 主要用于维护服务定义信息
    private NamingMaintainService namingMaintainService;
    // 监听器列表
    private List<RegisterCenterListener> registerCenterListenerList = new CopyOnWriteArrayList<>();;
    @Override
    public void init(String registerAddress, String env) {
            this.registerAddress = registerAddress;
            this.env = env;
        try {
            this.namingMaintainService = NamingMaintainFactory.createMaintainService(registerAddress);
            this.namingService = NamingFactory.createNamingService(registerAddress);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(ServiceDefinition serviceDefinition, ServiceInstance serviceInstance) {
        try {
            // 构建nacos实例信息
            Instance nacosInstance = new Instance();
            nacosInstance.setInstanceId(serviceInstance.getServiceInstanceId());
            nacosInstance.setPort(serviceInstance.getPort());
            nacosInstance.setIp(serviceInstance.getIp());
            nacosInstance.setMetadata(ImmutableMap.of(GatewayConst.META_DATA_KEY, JSON.toJSONString(serviceInstance)));
            //注册服务
            namingService.registerInstance(serviceDefinition.getServiceId(),env,nacosInstance);
            // 更新服务定义——怎么元数据信息
            namingMaintainService.updateService(serviceDefinition.getServiceId(),env,0,
                    ImmutableMap.of(GatewayConst.META_DATA_KEY,JSON.toJSONString(serviceDefinition)));

            log.info("register {} {}",serviceDefinition,serviceInstance);

        } catch (NacosException e) {
           throw  new RuntimeException(e);
        }

    }

    @Override
    public void deRegister(ServiceDefinition serviceDefinition, ServiceInstance serviceInstance) {
        try {
            namingService.registerInstance(serviceDefinition.getServiceId(),
                    env,serviceInstance.getIp(),serviceInstance.getPort());
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void subscribeAllServices(RegisterCenterListener registerCenterListener) {
        registerCenterListenerList.add(registerCenterListener);

        //订阅所有的服务
        doSubscribeAllServices();
        // 可能有新服务加入，所以需要一个定时任务来检查
        ScheduledExecutorService scheduledThreadPool = Executors
                .newScheduledThreadPool(1, new NameThreadFactory("doSubscribeAllServices"));
        scheduledThreadPool.scheduleWithFixedDelay(() -> doSubscribeAllServices(),
                10,10, TimeUnit.SECONDS);

    }

    private void doSubscribeAllServices() {
        try {
            // 已经订阅的服务
            Set<String> subscribeService = namingService.getSubscribeServices().stream()
                    .map(ServiceInfo::getName).collect(Collectors.toSet());
            int pageNo = 1;
            int pageSize = 100;
            // nacos事件监听器
            EventListener eventListener = new NacosRegisterListener();

            // 分页从nacos拿到服务列表
            List<String> serviceList = namingService.getServicesOfServer(pageNo, pageSize, env).getData();
            while (CollectionUtils.isNotEmpty(serviceList)){
                log.info("service list size {}",serviceList.size());
                for (String service : serviceList) {
                    if(subscribeService.contains(service)){
                        continue;
                    }
                    //订阅对应的服务
                    namingService.subscribe(service,eventListener);
                    log.info("subscribe {} {}",service,env);
                }
                serviceList= namingService.getServicesOfServer(++pageNo, pageSize, env).getData();
            }
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    public class NacosRegisterListener implements EventListener {

        @Override
        public void onEvent(Event event) {
            if(event instanceof NamingEvent){
                NamingEvent namingEvent = (NamingEvent) event;
                String serviceName = namingEvent.getServiceName();
                try {
                    //获取服务定义信息
                    Service service = namingMaintainService.queryService(serviceName, env);
                    ServiceDefinition serviceDefinition = JSON.parseObject(service.getMetadata()
                    .get(GatewayConst.META_DATA_KEY),ServiceDefinition.class);
                    // 获取实例信息
                    List<Instance> allInstances = namingService.getAllInstances(serviceName, env);
                    Set<ServiceInstance> set = new HashSet<>();
                    for (Instance instance : allInstances) {
                        ServiceInstance serviceInstance = JSON.parseObject(instance.getMetadata()
                                .get(GatewayConst.META_DATA_KEY), ServiceInstance.class);
                        set.add(serviceInstance);
                    }
                    registerCenterListenerList.stream().forEach(l -> l.onChange(serviceDefinition,set
                    ));
                } catch (NacosException e) {
                  throw new RuntimeException(e);
                }
            }
        }
    }
}
