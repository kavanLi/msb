package com.mashibing.proxy.example03;

import java.util.Collections;
import java.util.List;

/**
 * 目标类
 * @author spikeCong
 * @date 2022/9/23
 **/
public class UserServiceImpl {

    //查询功能
    public List<User>  findUserList(){

        return Collections.singletonList(new User("tom",23));
    }
}
