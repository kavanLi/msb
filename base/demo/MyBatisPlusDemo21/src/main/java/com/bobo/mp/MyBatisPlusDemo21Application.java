package com.bobo.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc //for swagger solve Failed to start bean 'documentationPluginsBootstrapper' in spring data rest
public class MyBatisPlusDemo21Application {


    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusDemo21Application.class, args);
    }

}