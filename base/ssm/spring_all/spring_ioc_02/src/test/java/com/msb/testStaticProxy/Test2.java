package com.msb.testStaticProxy;

import com.msb.config.SpringConfig;
import com.msb.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test2 {
    @Test
    public void testGetBean(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext2.xml");
        UserServiceImpl userService = context.getBean("userServiceImpl", UserServiceImpl.class);
        userService.add();
    }

    @Test
    public void testGetBean2(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        UserServiceImpl userService = context.getBean("userServiceImpl", UserServiceImpl.class);
        userService.add();

    }
}
