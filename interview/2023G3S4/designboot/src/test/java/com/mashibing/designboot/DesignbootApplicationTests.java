package com.mashibing.designboot;

import com.mashibing.designboot.strategy.AbstractAuditStrategy;
import com.mashibing.designboot.strategy.HandlerContext;
import com.mashibing.designboot.strategy.pojo.Employee;
import com.mashibing.designboot.strategy.pojo.LeaveForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DesignbootApplicationTests {

    @Autowired
    private HandlerContext handlerContext;

    @Test
    public void test01(){

        //创建请假单
        LeaveForm form1 = new LeaveForm(new Employee("打工人", 1), "结婚", 4, 1);

        //返回符合条件的策略
        AbstractAuditStrategy auditStrategy = handlerContext.getAuditStrategy(form1);

        //通过策略类进行审核
        auditStrategy.audit(form1);
    }
}
