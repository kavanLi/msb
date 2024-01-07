package com.msb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
//@Configuration
public class GloableException  {
    @Bean
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties prop = new Properties();
        prop.put("java.lang.ArithmeticException","error1.jsp");
        prop.put("java.lang.NullPointerException","error2.jsp");
        resolver.setExceptionMappings(prop);
        return resolver;
    }
}
