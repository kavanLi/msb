package com.msb.testStaticProxy;

import com.msb.bean.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test4 {
    @Test
    public void testGetBean(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext4.xml");
        Student student1 = applicationContext.getBean("student1", Student.class);
        System.out.println(Arrays.toString(student1.getBooks()));
        System.out.println(student1.getBookSet());
        System.out.println(student1.getBookList());
        System.out.println(student1.getBookMap());
        System.out.println(student1.getBookList2());
    }
}
