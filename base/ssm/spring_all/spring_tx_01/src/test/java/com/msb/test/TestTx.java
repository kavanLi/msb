package com.msb.test;

import com.msb.config.SpringConfig;
import com.msb.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class TestTx {

    @Test
    public void testTransMoney(){
        ApplicationContext context =new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountService accountService = context.getBean(AccountService.class);
        int rows = accountService.transMoney(1, 2, 100);
        System.out.println(rows);
    }

    @Test
    public void testTransMoney2(){
        ApplicationContext context =new ClassPathXmlApplicationContext("applicationContext2.xml");
        AccountService accountService = context.getBean(AccountService.class);
        int rows = accountService.transMoney(1, 2, 100);
        System.out.println(rows);
    }

    @Test
    public void testTransMoney3(){
        ApplicationContext context =new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = context.getBean(AccountService.class);
        int rows = accountService.transMoney(1, 2, 100);
        System.out.println(rows);
    }

}
