package com.msb.testStaticProxy;

import com.msb.bean.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test2 {
    @Test
    public void testGetBean(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext2.xml");
        User user1 = applicationContext.getBean("user1", User.class);
        System.out.println(user1);
    }
}
