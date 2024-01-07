package com.msb.testStaticProxy;

import com.msb.bean.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test6 {
    @Test
    public void testGetBean(){
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext6.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println("第四步:User 获取");
        System.out.println(user);
        // 关闭容器
        applicationContext.close();
    }



}
