package com.bobo.mpdemo01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bobo.mpdemo01.bean.User;

/**
 * User对应的Service接口
 * 要使用MyBatisPlus的Service完成CRUD操作，得继承IService
 */
public interface IUserService extends IService<User> {

}
