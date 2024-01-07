package com.mashibing03.strategy;

import com.mashibing03.old.pojo.LeaveForm;

/**
 * @author spikeCong
 * @date 2023/3/21
 **/
public class AuditStrategyImpl_1 implements AuditStrategy {

    @Override
    public boolean isSupport(LeaveForm leaveForm) {
        return leaveForm.getDays() <= 3 && leaveForm.getType() == 1;
    }

    @Override
    public void audit(LeaveForm leaveForm) {
        System.out.println(leaveForm);
        System.out.println("三天以下婚丧假 无需审批自动通过!");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getName() {
        return "三天以下婚假审批规则";
    }
}
