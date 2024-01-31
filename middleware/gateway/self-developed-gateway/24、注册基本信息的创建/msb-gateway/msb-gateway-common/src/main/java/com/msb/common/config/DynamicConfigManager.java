package com.msb.common.config;

import javax.xml.ws.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 动态服务缓存配置管理类
 */
public class DynamicConfigManager {
    // 服务定义集合：uniqueId代表服务的唯一标识
    private ConcurrentHashMap<String /*uniquedId*/,ServiceDefinition> serviceDefinitionMap = new ConcurrentHashMap<>();
    // 服务的实力垃圾盒：uniqueId与一组服务实例对应
    private ConcurrentHashMap<String /*uniquedId*/, Set<ServiceInstance>> serviceInstanceMap = new ConcurrentHashMap<>();
    // 规则集合
    private ConcurrentHashMap<String /*ruleId*/,Rule> ruleMap = new ConcurrentHashMap<>();

    /***********************单例的处理*+****************/
    private DynamicConfigManager() {
    }
    private static class SingletonHolder{
        private static final DynamicConfigManager INSTANCE = new DynamicConfigManager();
    }
    public static DynamicConfigManager getInstance(){
        return SingletonHolder.INSTANCE;
    }
    /************对服务定义缓存进行操作的系列方法**************/
    //添加服务定义
    public void putServiceDefinition(String uniqueId,
                                     ServiceDefinition serviceDefinition){
        serviceDefinitionMap.put(uniqueId,serviceDefinition);

    }
    // 删除服务定义
    public void removeServiceDefinition(String uniqueId) {
        serviceDefinitionMap.remove(uniqueId);
    }
    // 获取服务定义信息
    public ServiceDefinition getServiceDefinition(String uniqueId) {
        return serviceDefinitionMap.get(uniqueId);
    }
    // 获取服务定义的Map
    public ConcurrentHashMap<String, ServiceDefinition> getServiceDefinitionMap() {
        return serviceDefinitionMap;
    }

    /******************对服务实例缓存进行操作的系列方法*************/
    // 获取服务实例
    public Set<ServiceInstance> getServiceInstanceByUniqueId(String uniqueId){
        return serviceInstanceMap.get(uniqueId);
    }
    // 添加单个服务实例
    public void addServiceInstance(String uniqueId,ServiceInstance serviceInstance){
        Set<ServiceInstance> set = serviceInstanceMap.get(uniqueId);
        set.add(serviceInstance);
    }
    // 添加服务实例列表
    public void addServiceInstance(String uniqueId,Set<ServiceInstance> serviceInstanceSet){
        serviceInstanceMap.put(uniqueId,serviceInstanceSet);
    }
    // 更新服务实例表表
    public void updateServiceInstance(String uniquedId,ServiceInstance serviceInstance){
        Set<ServiceInstance> set = serviceInstanceMap.get(uniquedId);
        Iterator<ServiceInstance> it = set.iterator();
        while(it.hasNext()){
            ServiceInstance instance = it.next();
            if(instance.getServiceInstanceId().equals(serviceInstance.getServiceInstanceId())){
                it.remove();
                break;
            }
        }
        set.add(serviceInstance);
    }
    // 删除服务实例
    public void removeServiceInstance(String uniqueId, String serviceInstanceId) {
        Set<ServiceInstance> set = serviceInstanceMap.get(uniqueId);
        Iterator<ServiceInstance> it = set.iterator();
        while(it.hasNext()) {
            ServiceInstance is = it.next();
            if(is.getServiceInstanceId().equals(serviceInstanceId)) {
                it.remove();
                break;
            }
        }
    }
    // 删除服务对应的所有实例
    public void  removeServiceInstancesByUniqueId(String uniqueId) {
        serviceInstanceMap.remove(uniqueId);
    }
    /***************** 	对规则缓存进行操作的系列方法 	***************/
    //  添加单个规则
    public void putRule(String ruleId, Rule rule) {
        ruleMap.put(ruleId, rule);
    }
    // 添加多个规则
    public void putAllRule(List<Rule> ruleList) {
        Map<String, Rule> map = ruleList.stream()
                .collect(Collectors.toMap(Rule::getId, r -> r));
        ruleMap = new ConcurrentHashMap<>(map);
    }
    // 获取规则
    public Rule getRule(String ruleId) {
        return ruleMap.get(ruleId);
    }
    // 删除规则
    public void removeRule(String ruleId) {
        ruleMap.remove(ruleId);
    }
    // 获取所有规则
    public ConcurrentHashMap<String, Rule> getRuleMap() {
        return ruleMap;
    }
}
