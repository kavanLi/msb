package com.msb.dao.impl;

import com.msb.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public int addUser(int userid, String username) {
        System.out.println("userdao add ... ...");
        return 1;
    }

}
