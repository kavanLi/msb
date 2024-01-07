package com.bobo.mpdemo01;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bobo.mpdemo01.bean.User;
import com.bobo.mpdemo01.mapper.UserMapper;
import com.bobo.mpdemo01.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MpDemo01ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 子查询
     * SELECT uid,name,age,email,is_deleted
     * FROM t_user
     * WHERE (
     *          uid IN (select uid from t_user where uid < 5)
     *      )
     */
    @Test
    void queryUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("uid","select uid from t_user where uid < 5")
        ;
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    /**
     * 更新用户Tom的age和邮箱信息
     * UPDATE t_user SET age=?,email=? WHERE (name = ?)
     */
    @Test
    void queryUser1() {
        String  name = "Tom";
        Integer age = null;
        String email = null;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.eq("name",name);
        }
        if(age != null && age > 0){
            wrapper.eq("age",age);
        }
        if(!StringUtils.isEmpty(email)){
            wrapper.eq("email",email);
        }
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void queryPage() {
        Page<User> page = new Page<>(1,5);
        Page<User> userPage = userMapper.selectPage(page, null);
        System.out.println("userPage.getCurrent() = " + userPage.getCurrent());
        System.out.println("userPage.getSize() = " + userPage.getSize());
        System.out.println("userPage.getTotal() = " + userPage.getTotal());
        System.out.println("userPage.getPages() = " + userPage.getPages());
        System.out.println("userPage.hasPrevious() = " + userPage.hasPrevious());
        System.out.println("userPage.hasNext() = " + userPage.hasNext());
    }

    /**
     * 删除所有年龄小于18岁的用户
     */
    @Test
    void deleteUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.le("age",18);
        int i = userMapper.delete(wrapper);
        System.out.println(i);
    }

}
