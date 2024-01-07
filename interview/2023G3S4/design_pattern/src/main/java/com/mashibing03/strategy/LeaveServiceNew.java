package com.mashibing03.strategy;

import com.mashibing03.old.pojo.LeaveForm;

/**
 * @author spikeCong
 * @date 2023/3/22
 **/
public class LeaveServiceNew {

   public void audit(LeaveForm leaveForm){
       AuditStrategyFactory factory = AuditStrategyFactory.getInstance();

       //返回符合条件的策略
       AuditStrategy strategy = factory.getAuditStrategy(leaveForm);

       //通过策略类进行审核
       strategy.audit(leaveForm);
   }
}
