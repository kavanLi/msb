package com.mashibing03.strategy;

import com.mashibing03.old.pojo.LeaveForm;

import java.util.ArrayList;
import java.util.List;

/**
 * 策略工厂
 * @author spikeCong
 * @date 2023/3/22
 **/
public class AuditStrategyFactory {

    //私有静态全局唯一 工厂对象
    private final static AuditStrategyFactory factory = new AuditStrategyFactory();

    //使用集合存储策略信息
    private List<AuditStrategy> auditStrategyList = new ArrayList<>();

    //私有构造,用于注册规则,(此处可以改为配置文件形式,实现规则动态配置)
    private AuditStrategyFactory(){

        auditStrategyList.add(new AuditStrategyImpl_1());
        auditStrategyList.add(new AuditStrategyImpl_2());
        auditStrategyList.add(new AuditStrategyImpl_3());
        auditStrategyList.add(new AuditStrategyImpl_4());
        auditStrategyList.add(new AuditStrategyImpl_5());
        auditStrategyList.add(new AuditStrategyImpl_6());
    }

    //全局访问点 获取单例对象
    public static AuditStrategyFactory getInstance(){

        return factory;
    }

    //获取匹配的策略
    public AuditStrategy getAuditStrategy(LeaveForm leaveForm){

        AuditStrategy auditStrategy = null;

        for (AuditStrategy strategy : auditStrategyList) {
            //找到匹配的规则
            if(strategy.isSupport(leaveForm)){
                if(auditStrategy == null){
                    auditStrategy = strategy;
                }else{
                    //优先级高的规则 替换现有的规则
                    //例如: 总经理请病假10,总经理优先级999,普通员工0
                    if(strategy.getPriority() > auditStrategy.getPriority()){
                        auditStrategy = strategy;
                    }
                }
            }
        }

        if(auditStrategy == null){
            throw new RuntimeException("没有匹配到请假审核规则");
        }else{
            return auditStrategy;
        }
    }

}
