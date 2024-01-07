package com.bobo.mpdemo01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bobo.mpdemo01.bean.User;
import com.bobo.mpdemo01.mapper.UserMapper;
import com.bobo.mpdemo01.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * Service的实现类
 * 必须继承ServiceImpl 并且在泛型中指定 对应的Mapper和实体对象
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}
