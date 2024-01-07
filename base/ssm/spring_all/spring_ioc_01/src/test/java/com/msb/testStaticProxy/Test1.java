package com.msb.testStaticProxy;

import com.msb.bean.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test1 {
    @Test
    public void testGetBean(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        //User user1 = applicationContext.getBean("user1", User.class);
        //System.out.println(user1);

        User user2 = applicationContext.getBean("user2", User.class);
        System.out.println(user2);

        User user3 = applicationContext.getBean("user3", User.class);
        System.out.println(user3);

        User user4 = applicationContext.getBean("user4", User.class);
        System.out.println(user4);

    }
}
