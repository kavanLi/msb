package com.msb.testStaticProxy;

import com.msb.bean.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test5 {
    @Test
    public void testGetBean(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext5.xml");
        Book book = applicationContext.getBean("book", Book.class);
        System.out.println(book);
    }



}
