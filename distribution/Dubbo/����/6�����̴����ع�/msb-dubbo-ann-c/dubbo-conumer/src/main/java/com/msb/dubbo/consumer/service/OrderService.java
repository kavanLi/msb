package com.msb.dubbo.consumer.service;

import com.msb.dubbo.service.IUserService;
import com.msb.dubbo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class OrderService {
    // 引用对应的dubbo服务
    @DubboReference
    private IUserService iUserService;
    public String createOrder(Long userId) {
        User user = iUserService.getUserById(userId);
        log.info("用户信息：{}" ,user);
        return "创建订单成功";
    }
}
