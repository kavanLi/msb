package com.mashibing.proxy.example01;

/**
 * 目标类
 * @author spikeCong
 * @date 2022/9/22
 **/
public class UserDaoImpl implements IUserDao {

    @Override
    public void save() {
        System.out.println("保存数据");
    }
}
