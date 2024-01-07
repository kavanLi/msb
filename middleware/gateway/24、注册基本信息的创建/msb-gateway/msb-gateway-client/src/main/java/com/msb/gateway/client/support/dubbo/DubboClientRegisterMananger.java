package com.msb.gateway.client.support.dubbo;

import com.google.gson.internal.PreJava9DateFormatProvider;
import com.msb.common.config.ServiceDefinition;
import com.msb.common.config.ServiceInstance;
import com.msb.common.constants.BasicConst;
import com.msb.common.constants.GatewayConst;
import com.msb.common.util.NetUtils;
import com.msb.common.util.TimeUtil;
import com.msb.gateway.client.core.ApiAnnotationScanner;
import com.msb.gateway.client.core.ApiProperties;
import com.msb.gateway.client.support.AbstractClientRegisterManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.ServiceBean;
import org.apache.dubbo.config.spring.context.event.ServiceBeanExportedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;
@Slf4j
public class DubboClientRegisterMananger extends AbstractClientRegisterManager implements ApplicationListener<ApplicationEvent> {

    private Set<Object> set = new HashSet<>();

    public DubboClientRegisterMananger(ApiProperties apiProperties) {
        super(apiProperties);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ServiceBeanExportedEvent){
            ServiceBean serviceBean = ((ServiceBeanExportedEvent)applicationEvent).getServiceBean();
            try {
                doRegisterDubbo(serviceBean);
            }catch (Exception e){
                log.error("doRegisterDubbo error",e);
                throw new RuntimeException(e);
            }
        }else if(applicationEvent instanceof ApplicationStartedEvent){
            log.info("dubbo api started");
        }
    }

    private void doRegisterDubbo(ServiceBean serviceBean) {
        Object bean = serviceBean.getRef();
        if(set.contains(bean)){
            return;
        }
        // 服务定义信息
        ServiceDefinition serviceDefinition = ApiAnnotationScanner.getInstance().scanner(bean, serviceBean);

        if(serviceDefinition == null){
            return;
        }

        serviceDefinition.setEnvType(getApiProperties().getEnv());

        // 服务实例信息
        ServiceInstance serviceInstance = new ServiceInstance();
        String localIp = NetUtils.getLocalIp();
        Integer port = serviceBean.getProtocol().getPort();
        String serviceInstanceId = localIp + BasicConst.COLON_SEPARATOR + port;
        String uniqueId = serviceDefinition.getUniqueId();
        String version = serviceDefinition.getVersion();

        serviceInstance.setServiceInstanceId(serviceInstanceId);
        serviceInstance.setUniqueId(uniqueId);
        serviceInstance.setIp(localIp);
        serviceInstance.setPort(port);
        serviceInstance.setRegisterTime(TimeUtil.currentTimeMillis());
        serviceInstance.setVersion(version);
        serviceInstance.setWeight(GatewayConst.DEFAULT_WEIGHT);

        // 进行注册
        register(serviceDefinition,serviceInstance);

    }
}























