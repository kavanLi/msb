package com.msb.test;

import com.msb.pojo.Emp;
import com.msb.service.EmpService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test1 {
    @Test
    public void testEmpService(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        EmpService empService = context.getBean(EmpService.class);
        /*int empCount = empService.findEmpCount();
        System.out.println(empCount);*/


        /*Emp emp = empService.findByEmpno(7521);
        System.out.println(emp);*/

       /* List<Emp> emps = empService.findByDeptno(20);
        emps.forEach(System.out::println);*/

       /*int rows =empService.addEmp(new Emp(null, "TOM", "SALESMAN", 7521, new Date(), 2000.0, 100.0, 10));
        System.out.println(rows);*/

        /*int rows = empService.updateEmp(new Emp(7940, "JERRY", "MANAGER", 7839, new Date(), 3000.0, 0.0, 20));
        System.out.println(rows);*/

        int rows = empService.deleteEmp(7940);
        System.out.println(rows);

    }
}
