package com.mashibing03.strategy;

import com.mashibing03.old.pojo.Employee;
import com.mashibing03.old.pojo.LeaveForm;

/**
 * @author spikeCong
 * @date 2023/3/22
 **/
public class Client {

    public static void main(String[] args) {

        LeaveServiceNew leaveServiceNew = new LeaveServiceNew();
        LeaveForm form1 = new LeaveForm(new Employee("李总经理", 9), "甲流发烧", 10, 0);
        leaveServiceNew.audit(form1);

        LeaveForm form2 = new LeaveForm(new Employee("打工人1", 2), "甲流发烧", 2, 0);
        leaveServiceNew.audit(form2);

        LeaveForm form3 = new LeaveForm(new Employee("打工人2", 3), "结婚", 2, 1);
        leaveServiceNew.audit(form3);

        LeaveForm form4 = new LeaveForm(new Employee("打工人3", 4), "请年假,休息休息", 5, 2);
        leaveServiceNew.audit(form4);
    }
}
