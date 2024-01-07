package com.mashibing03.strategy;

import com.mashibing03.old.pojo.LeaveForm;

/**
 * 审核策略接口
 * @author spikeCong
 * @date 2023/3/21
 **/
public interface AuditStrategy {

    //判断条件是否匹配(识别是哪一个策略)
    public boolean isSupport(LeaveForm leaveForm);

    //审核业务逻辑
    public void audit(LeaveForm leaveForm);

    //规则冲突时的优先级
    public int getPriority();

    //规则名称
    public String getName();
}
