package com.msb.dao.impl;

import com.msb.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Repository
public class UserDaoImplA implements UserDao {
    @Override
    public void add() {
        System.out.println("UserDaoImplA add ... ...");
    }
}
