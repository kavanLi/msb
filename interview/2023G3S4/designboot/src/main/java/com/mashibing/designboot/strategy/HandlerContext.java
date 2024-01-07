package com.mashibing.designboot.strategy;

import com.mashibing.designboot.strategy.pojo.LeaveForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spikeCong
 * @date 2023/3/22
 **/
@Service(value = "HandlerContext")
public class HandlerContext {

    //使用集合存储策略信息
    private List<AbstractAuditStrategy> strategyList = new ArrayList<>();

    //注册策略
    public void register(AbstractAuditStrategy auditStrategy){
        strategyList.add(auditStrategy);
    }

    //获取对应策略
    public AbstractAuditStrategy getAuditStrategy(LeaveForm leaveForm){

        AbstractAuditStrategy auditStrategy = null;

        for (AbstractAuditStrategy strategy : strategyList) {
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
