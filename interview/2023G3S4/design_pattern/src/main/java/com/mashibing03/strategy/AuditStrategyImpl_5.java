package com.mashibing03.strategy;

import com.mashibing03.old.pojo.LeaveForm;

/**
 * @author spikeCong
 * @date 2023/3/21
 **/
public class AuditStrategyImpl_5 implements AuditStrategy {

    @Override
    public boolean isSupport(LeaveForm leaveForm) {
        return leaveForm.getDays() > 1 && leaveForm.getType() == 0;
    }

    @Override
    public void audit(LeaveForm leaveForm) {
        System.out.println(leaveForm);
        System.out.println("一天以上病假进入审批流程!");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getName() {
        return "一天以上病假审批规则";
    }
}
