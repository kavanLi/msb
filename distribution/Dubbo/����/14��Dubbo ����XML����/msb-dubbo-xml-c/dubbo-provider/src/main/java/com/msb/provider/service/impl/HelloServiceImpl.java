package com.msb.provider.service.impl;

import com.msb.service.HelloServiceAPI;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImpl implements HelloServiceAPI {
    @Override
    public String sayHello(String message) {
        log.info("接收到消息：{}",message);
        return message;
    }
}
