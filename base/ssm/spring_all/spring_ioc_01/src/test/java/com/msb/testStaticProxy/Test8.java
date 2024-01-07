package com.msb.testStaticProxy;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test8 {
    @Test
    public void testGetBean() throws SQLException {
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext8.xml");
        DruidDataSource dataSource = applicationContext.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
    }



}
