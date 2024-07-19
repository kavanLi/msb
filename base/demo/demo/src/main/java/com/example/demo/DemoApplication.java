package com.example.demo;

import com.mashibing.internal.common.config.OkHttpConfig;
import com.mashibing.internal.common.config.OkHttp3Client;
import com.mashibing.internal.common.config.TraceFilter;
import com.mashibing.internal.common.interceptor.RateLimitInterceptor;
import com.mashibing.internal.common.interceptor.feigh.FeignInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *  下面的写法代表直接在当前项目引入另外一个模块的代码
 *  Spring Boot 应用程序启动时，会自动扫描 com.allinpay.smartyunst.security 包及其子包中的 Spring 组件，并将它们注册到 Spring 容器中。
 *         <dependency>
 *             <groupId>com.allinpay.smartyunst</groupId>
 *             <artifactId>smartyunst-security</artifactId>
 *             <version>1.0-SNAPSHOT</version>
 *         </dependency>
 *  @SpringBootApplication(scanBasePackages = {"com.allinpay.smartyunst.security"})
 */
@SpringBootApplication
@EnableAsync
@Import({TraceFilter.class, FeignInterceptor.class, OkHttp3Client.class, OkHttpConfig.class, RateLimitInterceptor.class})
@Slf4j
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Value("${spring.profiles.active}")
    private String activeProfile;


    @Bean
    public ApplicationRunner printActiveProfile(Environment environment) {
        return args -> {
            //String activeProfile = environment.getProperty("spring.profiles.active");
            log.info("**********************************************Active profile: {}", activeProfile);
        };
    }
}
