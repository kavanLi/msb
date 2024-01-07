package com.msb.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**

 *类说明：springboot的启动类
 */
@SpringBootApplication
public class SpringbootWithApplication {
    //启动方法(相当于启动一个Tomcat,同时默认端口8080)
    public static void main(String[] args) {
        SpringApplication.run(SpringbootWithApplication.class, args);
    }
}
