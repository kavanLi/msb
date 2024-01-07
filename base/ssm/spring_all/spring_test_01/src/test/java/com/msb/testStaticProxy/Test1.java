package com.msb.testStaticProxy;

import com.msb.dao.EmpDao;
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
        // 获取容器
        ApplicationContext applicationContext  =new ClassPathXmlApplicationContext("spring.xml");
        EmpDao empDao=applicationContext.getBean("empDao",EmpDao.class);
        empDao.addEmp();
    }

}
