package com.msb.testStaticProxy;

import com.msb.bean.Cat;
import com.msb.bean.Mouse;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test3 {
    @Test
    public void testGetBean(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext3.xml");
        Mouse mouse1 = applicationContext.getBean("mouse1", Mouse.class);
        System.out.println(mouse1);

        Cat cat1 = applicationContext.getBean("cat1", Cat.class);
        System.out.println(cat1);

        Cat cat2 = applicationContext.getBean("cat2", Cat.class);
        System.out.println(cat2);
    }
}
