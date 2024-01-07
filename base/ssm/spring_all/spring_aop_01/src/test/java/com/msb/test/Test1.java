package com.msb.test;

import com.msb.config.SpringConfig;
import com.msb.dao.EmpDao;
import com.msb.dao.UserDao;
import com.msb.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test1 {

    @Test
    public void getBean(){
        ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userdao = context.getBean(UserDao.class);
        int rows = userdao.addUser(1, "zhangsan");

    }

    @Test
    public void getBean2(){
        ApplicationContext context= new AnnotationConfigApplicationContext(SpringConfig.class);
        UserDao userdao = context.getBean(UserDao.class);
        int rows = userdao.addUser(1, "zhangsan");
    }


}
