package com.msb.gateway.client.core;

import com.msb.common.config.DubboServiceInvoker;
import com.msb.common.config.HttpServiceInvoker;
import com.msb.common.config.ServiceDefinition;
import com.msb.common.config.ServiceInvoker;
import com.msb.common.constants.BasicConst;
import com.msb.gateway.client.support.dubbo.DubboConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.spring.ServiceBean;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解扫描类
 */
public class ApiAnnotationScanner {
    /************单例***************/
    private ApiAnnotationScanner(){}

    private static class SingletonHolder{
        static final ApiAnnotationScanner INSTANCE = new ApiAnnotationScanner();
    }

    public static ApiAnnotationScanner getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public ServiceDefinition scanner(Object bean, Object... args){

        Class<?> aClass = bean.getClass();
        if(!aClass.isAnnotationPresent(ApiService.class)){
            return null;
        }
        ApiService apiService = aClass.getAnnotation(ApiService.class);
        String serviceId = apiService.serviceId();
        ApiProtocol protocol = apiService.protocol();
        String patternPath = apiService.patternPath();
        String version = apiService.version();


        Map<String, ServiceInvoker> invokerMap = new HashMap<>();

        Method[] methods = aClass.getMethods();
        if(methods != null && methods.length > 0){
            for (Method method : methods) {
                ApiInvoker apiInvoker = method.getAnnotation(ApiInvoker.class);
                if(apiInvoker == null){
                    continue;
                }
                String path = apiInvoker.path();
                switch (protocol){
                    case HTTP:
                        HttpServiceInvoker httpServiceInvoker = createHttpServiceInvoker(path);
                        invokerMap.put(path,httpServiceInvoker);
                        break;
                    case DUBBO:
                        ServiceBean<?> serviceBean  = (ServiceBean<?>) args[0];
                        DubboServiceInvoker dubboServiceInvoker = createDubboServiceInvoker(path,serviceBean,method);

                        String dubboVersion = dubboServiceInvoker.getVersion();
                        if(!StringUtils.isBlank(dubboVersion)){
                            version = dubboVersion;
                        }
                        invokerMap.put(path,dubboServiceInvoker);
                        break;
                    default:
                        break;
                }
            }
            // 创建服务定义
            ServiceDefinition serviceDefinition = new ServiceDefinition();
            serviceDefinition.setUniqueId(serviceId + BasicConst.COLON_SEPARATOR + version);
            serviceDefinition.setServiceId(serviceId);
            serviceDefinition.setVersion(version);
            serviceDefinition.setProtocol(protocol.getCode());
            serviceDefinition.setPatternPath(patternPath);
            serviceDefinition.setEnable(true);
            return serviceDefinition;
        }
        return null;
    }
    /**
     *  构建DubboServiceInvoker对象
     *
     */
    private DubboServiceInvoker createDubboServiceInvoker(String path, ServiceBean<?> serviceBean, Method method) {
        DubboServiceInvoker dubboServiceInvoker = new DubboServiceInvoker();
        dubboServiceInvoker.setInvokerPath(path);

        String methodName = method.getName();
        String registerAddress = serviceBean.getRegistry().getAddress();
        String interfaceClass = serviceBean.getInterface();

        dubboServiceInvoker.setRegisterAddress(registerAddress);
        dubboServiceInvoker.setMethodName(methodName);
        dubboServiceInvoker.setInterfaceClass(interfaceClass);

        String[] parameterTypes = new String[method.getParameterCount()];
        Class<?>[] classes = method.getParameterTypes();
        for(int i = 0;i < classes.length;i ++){
            parameterTypes[i]  = classes[i].getName();
        }
        dubboServiceInvoker.setParameterTypes(parameterTypes);

        Integer serviceTimout = serviceBean.getTimeout();
        //如果我们没有设置超时时间，就获取默认超时间
        if (serviceTimout == null || serviceTimout.intValue() == 0) {
           serviceTimout = DubboConstants.DUBBO_TIMEOUT;
        }
        dubboServiceInvoker.setTimeout(serviceTimout);
        String dubboVersion = serviceBean.getVersion();
        dubboServiceInvoker.setVersion(dubboVersion);
        return dubboServiceInvoker;
    }

    /**
     * 构建HTTPServiceInvoker对象
     * @param path
     * @return
     */
    private HttpServiceInvoker createHttpServiceInvoker(String path) {
        HttpServiceInvoker httpServiceInvoker = new HttpServiceInvoker();
        httpServiceInvoker.setInvokerPath(path);
        return httpServiceInvoker;
    }

}











































