package com.mashibing03.strategy;

import com.mashibing03.old.pojo.LeaveForm;

/**
 * @author spikeCong
 * @date 2023/3/21
 **/
public class AuditStrategyImpl_3 implements AuditStrategy {

    @Override
    public boolean isSupport(LeaveForm leaveForm) {
        //级别9 总经理
        return leaveForm.getEmployee().getLevle() == 9;
    }

    @Override
    public void audit(LeaveForm leaveForm) {
        System.out.println(leaveForm);
        System.out.println("总经理请假无需审批自动通过!");
    }

    @Override
    public int getPriority() {
        return 999;
    }

    @Override
    public String getName() {
        return "总经理请假审批规则";
    }
}
