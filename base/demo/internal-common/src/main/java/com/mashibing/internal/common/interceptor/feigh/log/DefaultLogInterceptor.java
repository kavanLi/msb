package com.mashibing.internal.common.interceptor.feigh.log;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-12 13:53
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public class DefaultLogInterceptor implements FeignLogInterceptor {
    @Override
    public void request(String target, String url, String body) {
        log.debug("feign targetMethod={}, request url={}, body={}", target, url, body);
    }

    @Override
    public void exception(String target, String url, FeignException feignException) {
        log.debug("feign targetMethod={}, exception url={}, body={}", target, url, feignException.getMessage());
    }

    @Override
    public void response(String target, String url, Object response) {
        log.debug("feign targetMethod={}, response url={}, body={}", target, url, response);
    }

}
