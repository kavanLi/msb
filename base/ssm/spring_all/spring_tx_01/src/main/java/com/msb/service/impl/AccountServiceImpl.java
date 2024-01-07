package com.msb.service.impl;

import com.msb.dao.AccountDao;
import com.msb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional// 仅仅为当前方法添加事务控制
    public int transMoney(int from, int to, int money) {
        int rows =0;
        // 转出操作
        rows += accountDao.transMoney(from, 0-money);
        // 模拟异常
        //int i =1/0;
        // 转入操作
        rows += accountDao.transMoney(to, money);
        return rows;
    }
}
