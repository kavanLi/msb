package com.mashibing.internal.common.interceptor.feigh.log;

import feign.FeignException;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-12 13:52
 * To change this template use File | Settings | File and Code Templates.
 */

public interface FeignLogInterceptor {

    void request(String target, String url, String body);

    void exception(String target, String url, FeignException feignException);

    void response(String target, String url, Object response);

}
