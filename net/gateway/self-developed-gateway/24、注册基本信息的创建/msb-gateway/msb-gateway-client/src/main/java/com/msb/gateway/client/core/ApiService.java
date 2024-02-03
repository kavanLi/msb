package com.msb.gateway.client.core;

import java.lang.annotation.*;

/**
 * 服务定义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiService {
    String serviceId();

    String version() default "1.0.0";

    ApiProtocol protocol();

    String patternPath();
}
