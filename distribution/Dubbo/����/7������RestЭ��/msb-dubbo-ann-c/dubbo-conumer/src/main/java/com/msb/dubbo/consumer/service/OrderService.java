package com.msb.dubbo.consumer.service;

import com.msb.dubbo.service.IUserService;
import com.msb.dubbo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderService {
    // 引用对应的dubbo服务
    @DubboReference(protocol = "rest")
    private IUserService iUserService;
    @Autowired
    private RestTemplate restTemplate;
    public String createOrder(Long userId) {
        User user = iUserService.getUserById(userId);
      //  User user = restTemplate.getForObject("http://localhost:20884/user/232", User.class);
        log.info("用户信息：{}" ,user);
        return "创建订单成功";
    }
}
