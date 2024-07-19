package com.msb.caffeine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.msb.caffeine.bean.User;
import com.msb.caffeine.cache.CacheType;
import com.msb.caffeine.cache.DoubleCache;
import com.msb.caffeine.mapper.UserMapper;
import com.msb.caffeine.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Service的实现类
 * 必须继承ServiceImpl 并且在泛型中指定 对应的Mapper和实体对象
 */
@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Cache cache;

    /**
     * 在 Spring 的缓存机制中，@Cacheable 注解用于从缓存中读取数据或将方法的返回结果存储到缓存中。具体来说，value 和 key 这两个属性用于配置缓存的命名和键的生成。以下是它们的详细解释：
     *
     * value 属性：
     *
     * value 用于指定缓存的名称或命名空间。可以理解为这是缓存存储的容器名。在您的代码中，value = "user" 表示将缓存存储在名为 user 的缓存容器中。这个名称可以对应于具体的缓存实现，如 Redis 或 Caffeine，或者它们的组合。
     * key 属性：
     *
     * key 用于指定缓存项的键，确定缓存中每个条目的唯一标识符。键的值可以是一个简单的字符串，也可以是根据方法参数动态生成的。在您的代码中，key = "#userId" 表示使用方法参数 userId 作为缓存键。通过 Spring 表达式语言（SpEL），可以引用方法参数、方法返回值等。
     *
     * value = "user"：缓存容器的名称是 "user"。
     * key = "#userId"：缓存项的键是方法参数 userId 的值。
     * 当 query2_2 方法被调用时，Spring 首先会检查名为 user 的缓存容器中是否存在键为 userId 的缓存数据。如果存在，直接返回缓存数据，不执行方法体。如果不存在，执行方法体，将结果存储到缓存中，并返回结果。
     *
     * 关于您的具体实现，`queryquery2_2 方法首先尝试从 Redis 中获取缓存数据，如果 Redis 中没有找到对应的数据，则从数据库查询并将结果存储到 Redis 中。这种实现方式结合了 @Cacheable 注解，使得方法级缓存与 Redis 缓存共同工作，提高了缓存的命中率和性能。
     *
     * @param userId
     * @return
     */

    //Caffeine+Redis两级缓存查询-- 使用注解 , unless = "#result == null" -> 方法返回null不进行缓存
    //@Cacheable:
    // 根据键从缓存中取值，如果缓存存在，那么获取缓存成功之后，直接返回这个缓存的结果。如果缓存不存在，那么执行方法，并将结果放入缓存中。
    @Cacheable(value = "user", key = "#userId", unless = "#result == null")
    public User query2_2(long userId){
        String key = "user-"+userId;
        //先查询 Redis  （2级缓存）
        Object obj = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(obj)) {
            log.info("get data from redis:"+key);
            return (User)obj;
        }
        // Redis没有则查询 DB（MySQL）
        User user = userMapper.selectById(userId);
        log.info("get data from database:"+userId);
        redisTemplate.opsForValue().set(key, user, 30, TimeUnit.SECONDS);

        return user;
    }

    /**
     * 在 Spring 的 @Cacheable 注解中，key 表达式通常只支持 SpEL (Spring Expression Language) 表达式。直接使用常量和变量的拼接是不可行的，需要通过 SpEL 表达式来实现这一点。
     *
     * 要在 @Cacheable 注解的 key 属性中组合常量和变量，可以使用 SpEL 表达式，并在表达式中引用常量和方法参数。Spring 允许你使用 SpEL 来引用方法参数以及类的静态字段和静态方法。
     *
     * 为了将常量与方法参数组合，你可以采取以下步骤：
     *
     * 定义静态常量：确保你的常量是静态的。
     * 使用 SpEL 表达式：在 key 属性中使用 SpEL 表达式来引用常量和方法参数。
     *
     * 解释
     * T(com.example.WxConstants).WX_TOKEN_KEY：通过 T() 表达式引用类的静态字段。
     * #wxAppId：引用方法参数 wxAppId。
     *
     * Caffeine+Redis两级缓存查询-- 使用注解
     * @param wxAppId
     * @return
     */

    /**
     * Caffeine+Redis两级缓存查询-- 使用注解
     * @Cacheable注解不生效
     * 在同一个类中，一个方法调用另外一个有注解（比如 @Cacheable，@Async，@Transational）的方法，注解是不会生效的。
     * @return
     */
    @Override
    @Cacheable(value = WxConstants.WX_SESSION_CACHE_NAME, key = "#key", unless = "#result == null")
    public JSONObject getWxSessionFromCache(String key, String code) {
        // 如果caffeine没有缓存数据则先查询 Redis（2级缓存）
        Object obj = redisService.get(key);
        if (Objects.nonNull(obj)) {
            log.info("get data from redis:" + key);
            return (JSONObject) obj;
        }

        // Redis也没有则重新从wx服务器获取wxAccessToken
        JSONObject jsonObject = wxServerApiService.jscode2session(code);
        log.info("get wxSession from weixin server: {}", jsonObject);

        Duration duration = Duration.ofHours(24);
        // 更新到Redis
        redisService.set(key, jsonObject, duration);

        // 设置过期时间 -> 去CustomExpire配置该缓存的过期时间
        //jsonObject.put(WxConstants.WX_EXPIRE_KEY, duration.toNanos());

        // 发送消息到Redis频道(让其他应用把Caffeine缓存失效掉)，以达到清除脏数据的作用。
        // 这样可以确保下次访问该缓存项时，不会直接从缓存中读取，而是执行相应的方法来获取新数据并重新缓存。
        String message = RedisMessageListener.SERVICE_ID + ":" + key;
        // -> 去RedisMessageListener配置该缓存的处理逻辑
        redisTemplate.convertAndSend(WxConstants.REDIS_LISTENER_CHANNEL, message);

        // 更新到本地Caffeine
        return jsonObject;
    }

    //只有当userId为偶数时才会进行缓存
    @Cacheable(value = "user", key = "#userId", condition="#userId%2==0", unless = "#result == null")
    public User query2_3(long userId){
        String key = "user-"+userId;
        //先查询 Redis  （2级缓存）
        Object obj = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(obj)) {
            log.info("get data from redis:"+key);
            return (User)obj;
        }
        // Redis没有则查询 DB（MySQL）
        User user = userMapper.selectById(userId);
        log.info("get data from database:"+userId);
        redisTemplate.opsForValue().set(key, user, 30, TimeUnit.SECONDS);

        return user;
    }
    public void deleteCache(String cacheType) {
        cache.invalidate(cacheType);
    }

    //清除缓存(所有的元素)
    @CacheEvict(value="user", key = "#userId",allEntries=true)
    public void deleteAll(long userId) {
        System.out.println(userId);
    }
    //beforeInvocation=true：在调用该方法之前清除缓存中的指定元素
    @CacheEvict(value="user", key = "#userId",beforeInvocation=true)
    public void delete(long userId) {
        System.out.println(userId);
    }

    //不管之前的键对应的缓存是否存在，都执行方法，并将结果强制放入缓存。
    @CachePut(value="user", key = "#userId")
    public void CachePut(long userId) {
        System.out.println(userId);
    }













    @DoubleCache(cacheName = "user", key = "#userId",
            type = CacheType.PUT)
    public User query3(Long userId) {
        User user = userMapper.selectById(userId);
        return user;
    }

    @DoubleCache(cacheName = "user",key = "#user.userId",
            type = CacheType.PUT)
    public int update3(User user) {
        return userMapper.updateById(user);
    }

    @DoubleCache(cacheName = "user",key = "#user.userId",
            type = CacheType.DELETE)
    public void deleteOrder(User user) {
        userMapper.deleteById(user);
    }
}
