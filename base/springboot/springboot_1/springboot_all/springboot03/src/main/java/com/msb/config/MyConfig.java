package com.msb.config;

import com.msb.pojo.Emp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Configuration
public class MyConfig {
    @Bean
    public Emp getEmp(){
        Emp emp =new Emp();
        emp.setEname("aaa");
        return emp;
    }

}
