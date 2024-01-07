package com.mashibing.proxy.demo01;

/**
 * @author spikeCong
 * @date 2023/3/19
 **/
//静态代理对象：UserDaoProxy 需要实现IUserDao接口
public class UserDaoProxy implements IUserDao {

    private IUserDao target;

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }

    @Override
    public void save() {
        System.out.println("开启事务"); //扩展额外功能
        target.save();
        System.out.println("提交事务");
    }
}