package com.bobo.mp;

import com.bobo.mp.mapper.UserMapper;
import com.bobo.mp.pojo.User;
import com.bobo.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MyBatisPlusDemo21ApplicationTests1 {

    @Autowired
    private UserMapper mapper;

    @Test
    void queryAllUser() {
        List<User> users = mapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void addUser() {
        User user = new User();
        user.setUserName("boge");
        user.setAge(18);
        int i = mapper.insert(user);
        System.out.println(i);
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setUid(1516968812076421122l);
        user.setUserName("波哥");
        user.setAge(20);
        int i = mapper.updateById(user);
        System.out.println(i);
    }

    @Test
    void deleteById() {
        int i = mapper.deleteById(1516968812076421122l);
        System.out.println("i = " + i);
    }

    @Test
    void deleteByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("id",6);
        map.put("name","tom");
        int i = mapper.deleteByMap(map);
        System.out.println("i = " + i);
    }

    @Test
    void deleteBatchById() {
        // delete from user where id in [1,2,3,4]
        int i = mapper.deleteBatchIds(Arrays.asList(1l, 2l, 3l, 4l));
        System.out.println("i = " + i);
    }

    @Test
    void selectList() {
        List<User> users = mapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void selectById() {
        User user = mapper.selectById(3);
        System.out.println("user = " + user);
    }

    @Test
    void selectBatchById() {
        List<User> users = mapper.selectBatchIds(Arrays.asList(1, 2, 3, 4, 5));
        users.forEach(System.out::println);
    }

    @Test
    void selectByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("id",1);
        map.put("name","Jone");
        List<User> users = mapper.selectByMap(map);
        users.forEach(System.out::println);
    }


    @Autowired
    private IUserService userService;


    @Test
    void selectByID1() {
        User user = userService.getById(1);
        System.out.println(user);
    }

    @Test
    void selectList1() {
        List<User> list = userService.list();
        list.forEach(System.out::println);
    }

    @Test
    void save() {
        User user = new User();
        user.setUserName("boge");
        user.setAge(18);
        boolean flag = userService.save(user);
        System.out.println("flag = " + flag);
    }

    @Test
    void saveBatch() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserName("boge"+i);
            user.setAge(18+i);
            list.add(user);
        }
        // 批量插入的时候，我们最好指定分批的条数
        boolean flag = userService.saveBatch(list,50);
        System.out.println("flag = " + flag);
    }

    @Test
    void remove() {
        userService.removeById(1);
    }
}
