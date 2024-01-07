package com.mashibing.designboot.strategy;

import com.mashibing.designboot.strategy.pojo.LeaveForm;
import org.springframework.stereotype.Service;

/**
 * @author spikeCong
 * @date 2023/3/21
 **/
@Service
public class AuditStrategyImpl_2 extends AbstractAuditStrategy{

    @Override
    public boolean isSupport(LeaveForm leaveForm) {
        return leaveForm.getDays() > 3 && leaveForm.getType() == 1;
    }

    @Override
    public void audit(LeaveForm leaveForm) {
        System.out.println(leaveForm);
        System.out.println("三天以上婚丧假 进入上级审批流程!");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getName() {
        return "三天以上婚丧假审批规则";
    }
}
