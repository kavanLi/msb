package com.msb.dubbo.provider.service.impl;

import com.msb.dubbo.bean.User;
import com.msb.dubbo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// 定义一个Dubbo服务
@Slf4j
@DubboService
public class UserServiceImpl  implements IUserService {


    @Override
    public User getUserById( Long id) {
        log.info("获取用户信息 userId:{}",id);
        User user = User.builder().id(id)
                .age(12)
                .name("天涯")
                .build();
        return user;
    }
}
