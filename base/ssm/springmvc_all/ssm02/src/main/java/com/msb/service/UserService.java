package com.msb.service;

import com.msb.pojo.User;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface UserService {
    User findUser(String uname, String password);
}
