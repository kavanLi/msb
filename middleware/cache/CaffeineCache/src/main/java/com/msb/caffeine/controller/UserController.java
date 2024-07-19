package com.msb.caffeine.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.msb.caffeine.bean.User;
import com.msb.caffeine.mapper.UserMapper;
import com.msb.caffeine.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 李瑾老师
 * 类说明：订单相关的类
 */
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Cache cache;

    @Autowired
    private UserService userService;

    //http://127.0.0.1:8080/queryUserById?userId=1
    @RequestMapping("/queryUserById")
    public User queryUserById(@RequestParam("userId")long userId){
        return  userService.query3(userId);//使用手写的两级缓存注解
    }

    @RequestMapping("/updateUser")
    public User updateUser(@RequestBody User user){
        log.info("update order user");
        String key = "user-"+user.getId();
        userMapper.updateById(user);
        //修改 Redis
        redisTemplate.opsForValue().set(key,user,30, TimeUnit.SECONDS);
        // 修改本地缓存
        cache.put(key,user);
        return user;
    }

    @RequestMapping("/deleteUser")
    public void deleteUser(@RequestBody  User user){
        log.info("delete  user");
        String key = "user-"+user.getId();
        userMapper.deleteById(user);
        redisTemplate.delete(key);
        cache.invalidate(key);
    }

    //只查数据库
    public User query1_1(long userId){
        User user = userMapper.selectById(userId);
        log.info("query1_1 方法结束");
        return user;
    }
    //Caffeine+Redis两级缓存查询
    public User query1_2(long userId){
        String key = "user-"+userId;
        User user = (User) cache.get(key,
                k -> {
                    //先查询 Redis  （2级缓存）
                    Object obj = redisTemplate.opsForValue().get(key);
                    if (Objects.nonNull(obj)) {
                        log.info("get data from redis:"+key);
                        return obj;
                    }
                    // Redis没有则查询 DB（MySQL）
                    User user2 = userMapper.selectById(userId);
                    log.info("get data from database:"+userId);
                    redisTemplate.opsForValue().set(key, user2, 30, TimeUnit.SECONDS);
                    return user2;
                });
        return user;
    }







//    public void update1_1(User order) {
//        log.info("update User data");
//        String key="user-" + order.getId();
//        userMapper.updateById(order);
//        //修改 Redis
//        redisTemplate.opsForValue().set(key,order,120, TimeUnit.SECONDS);
//        // 修改本地缓存
//        cache.put(key,order);
//    }


//    public void delete1_1(long userId) {
//        log.info("delete User");
//        userMapper.deleteById(userId);
//        String key="user-" + userId;
//        redisTemplate.delete(key);
//        cache.invalidate(key);
//    }








}
