package com.msb.test;

import com.msb.config.SpringConfig;
import com.msb.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestTx2 {
    @Autowired
    private AccountService accountService;
    @Test
    public void testTransMoney3(){
        int rows = accountService.transMoney(1, 2, 100);
        System.out.println(rows);
    }

}
