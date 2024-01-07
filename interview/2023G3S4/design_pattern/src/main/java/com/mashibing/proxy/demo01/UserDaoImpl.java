package com.mashibing.proxy.demo01;

/**
 * @author spikeCong
 * @date 2023/3/19
 **/
//目标对象：UserDaoImpl
public class UserDaoImpl implements IUserDao {
    @Override
    public void save() {
        System.out.println("保存数据");
    }
}

