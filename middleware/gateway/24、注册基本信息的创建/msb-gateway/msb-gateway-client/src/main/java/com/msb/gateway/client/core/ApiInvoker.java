package com.msb.gateway.client.core;

import java.lang.annotation.*;

/**
 * 必须在方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiInvoker {
    String path();
}
