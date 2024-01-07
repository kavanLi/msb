package com.msb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

@ComponentScan(basePackages = "com.msb")
@PropertySource("classpath:aaa.properties")
public class SpringConfig {
}
