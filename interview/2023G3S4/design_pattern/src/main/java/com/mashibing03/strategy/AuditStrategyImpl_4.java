package com.mashibing03.strategy;

import com.mashibing03.old.pojo.LeaveForm;

/**
 * @author spikeCong
 * @date 2023/3/21
 **/
public class AuditStrategyImpl_4 implements AuditStrategy {

    @Override
    public boolean isSupport(LeaveForm leaveForm) {
        return leaveForm.getDays() == 1 && leaveForm.getType() == 0;
    }

    @Override
    public void audit(LeaveForm leaveForm) {
        System.out.println(leaveForm);
        System.out.println("一天病假无需审批自动通过!");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getName() {
        return "一天病假审批规则";
    }
}
