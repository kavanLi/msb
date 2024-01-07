package com.msb.service.impl;

import com.msb.dao.UserDao;
import com.msb.dao.impl.UserDaoImplA;
import com.msb.dao.impl.UserDaoImplB;
import com.msb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Service
public class UserServiceImpl implements UserService {
    //@Autowired
    //@Qualifier("userDaoImplA")
    //@Qualifier("userDaoImplB")
    //private UserDao userDao;

    @Resource(name = "userDaoImplB")
    private UserDao userDao;

    // 普通数据类型的属性赋值 8+String
    @Value("${sname}")
    private String sname;
    @Value("${sgender}")
    private String sgender;
    @Value("${sage}")
    private Integer sage;




    @Override
    public void add() {
        System.out.println("UserServiceImpl add ... ...");
        System.out.println(sname);
        System.out.println(sgender);
        System.out.println(sage);
        userDao.add();
    }
}
