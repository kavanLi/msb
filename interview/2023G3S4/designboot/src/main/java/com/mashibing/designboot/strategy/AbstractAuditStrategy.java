package com.mashibing.designboot.strategy;


import com.mashibing.designboot.strategy.pojo.LeaveForm;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

/**
 * 通过实现 InitializingBean,搞定策略模式
 * InitializingBean接口提供了bean被BeanFactory设置了所有属性后的处理方法
 * @author spikeCong
 * @date 2023/3/22
 **/
public abstract class AbstractAuditStrategy implements InitializingBean {

    @Resource
    private HandlerContext handlerContext;

    //容器启动的时候自动调用该方法,完成策略注册
    @Override
    public void afterPropertiesSet() throws Exception {
        //执行注册操作,将实现类策略接口的策略类进行注册
        handlerContext.register(this);
    }












    //具体逻辑处理方法
    public abstract boolean isSupport(LeaveForm leaveForm);

    //审核业务逻辑
    public abstract void audit(LeaveForm leaveForm);

    //规则冲突时的优先级
    public abstract int getPriority();

    //规则名称
    public abstract String getName();

}
