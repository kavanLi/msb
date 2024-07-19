package com.msb.caffeine.service.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;
import com.allinpay.smartyunst.common.bean.response.RedisValueWithExpiry;
import com.allinpay.smartyunst.common.constants.WxConstants;
import com.allinpay.smartyunst.common.domain.dto.LoginMemberDTO;
import com.allinpay.smartyunst.mall.portal.domain.WxAccessToken;
import com.allinpay.smartyunst.mall.portal.service.WxServerApiService;
import com.allinpay.smartyunst.mybatis.mapper.UmsMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Redis操作Service实现类
 * Created by macro on 2020/3/3.
 */
@Service
@Slf4j
public class CaffeineCacheServiceImpl implements CaffeineCacheService {

    @Autowired
    private RedisService redisService;

    @Autowired
    @Lazy
    private WxServerApiService wxServerApiService;

    @Autowired
    @Qualifier("caffeineCacheManager")
    private CacheManager caffeineCacheManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UmsMemberMapper umsMemberMapper;


    @Value("${oa.wx.secret}")
    private String wxSecret;

    @Value("${oa.wx.appId}")
    private String wxAppId;

    @Override
    public void del(String cacheName, String key) {
        caffeineCacheManager.getCache(cacheName).evict(key);
    }

    @Override
    @Cacheable(value = WxConstants.MINI_CACHE_NAME, key = "#key", unless = "#result == null")
    public String getMiniLoginTokenFromCache(String key, String openId) {
        // 如果caffeine没有缓存数据则先查询 Redis（2级缓存）
        RedisValueWithExpiry <Object> valueAndExpiry = redisService.getValueAndExpiry(key);
        String token = String.valueOf(valueAndExpiry.getValue());
        if (StringUtils.isNotEmpty(token)) {
            log.info("get data from redis:" + key);
            return token;
        }

        // 更新到Redis
        Duration duration = Duration.ofHours(168);
        redisService.set(key, token, duration);

        // 发送消息到Redis频道(让其他应用把Caffeine缓存失效掉)，以达到清除脏数据的作用。
        // 这样可以确保下次访问该缓存项时，不会直接从缓存中读取，而是执行相应的方法来获取新数据并重新缓存。
        //String message = RedisMessageListener.SERVICE_ID + ":" + key;
        // -> 去RedisMessageListener配置该缓存的处理逻辑
        //redisTemplate.convertAndSend(WxConstants.REDIS_LISTENER_CHANNEL, message);

        // 更新到本地Caffeine, 设置过期时间 -> 去CustomExpire配置该缓存的过期时间
        return token;
    }


    /**
     * Caffeine+Redis两级缓存查询-- 使用注解
     *
     * @return
     */
    @Override
    @Cacheable(value = WxConstants.UMS_MEMBER_CACHE_NAME, key = "#key", unless = "#result == null")
    public LoginMemberDTO getUmsMemberFromCache(String key, String openId) {
        // 如果caffeine没有缓存数据则先查询 Redis（2级缓存）
        RedisValueWithExpiry <Object> valueAndExpiry = redisService.getValueAndExpiry(key);
        if (Objects.nonNull(valueAndExpiry.getValue())) {
            log.info("get data from redis:" + key);
            LoginMemberDTO loginMemberDTO = (LoginMemberDTO) valueAndExpiry.getValue();
            loginMemberDTO.setExpireIn(valueAndExpiry.getExpiryTime());
            return loginMemberDTO;
        }

        // Redis也没有则从数据库获取
        Map <String, Object> params = new HashMap <>();
        params.put("openId", openId);
        ////按创建时间进行排序
        //params.put("orderField", "create_time");
        //List <UmsMember> umsMemberList = umsMemberMapper.listUmsMember(params);
        List <com.allinpay.smartyunst.mybatis.dto.LoginMemberDTO> loginMemberDTOS = umsMemberMapper.getLoginMemberJoinList(params);
        if (CollectionUtils.isEmpty(loginMemberDTOS)) {
            return null;
        }
        LoginMemberDTO loginMemberDTO = new LoginMemberDTO();
        BeanUtils.copyProperties(loginMemberDTOS.get(0), loginMemberDTO);
        Duration duration = Duration.ofSeconds(168 * 60 * 60);
        loginMemberDTO.setExpireIn(duration.getSeconds());
        // 更新到Redis
        redisService.set(key, loginMemberDTO, duration);

        // 发送消息到Redis频道(让其他应用把Caffeine缓存失效掉)，以达到清除脏数据的作用。
        // 这样可以确保下次访问该缓存项时，不会直接从缓存中读取，而是执行相应的方法来获取新数据并重新缓存。
        //String message = RedisMessageListener.SERVICE_ID + ":" + key;
        // -> 去RedisMessageListener配置该缓存的处理逻辑
        //redisTemplate.convertAndSend(WxConstants.REDIS_LISTENER_CHANNEL, message);

        // 更新到本地Caffeine, 设置过期时间 -> 去CustomExpire配置该缓存的过期时间
        return loginMemberDTO;
    }

    /**
     * Caffeine+Redis两级缓存查询-- 使用注解
     *
     * @param key
     * @return
     */
    @Override
    @Cacheable(value = WxConstants.WX_TOKEN_CACHE_NAME, key = "#key", unless = "#result == null")
    public WxAccessToken getWxAccessTokenFromCache(String key) {
        // 如果caffeine没有缓存数据则先查询 Redis（2级缓存）
        RedisValueWithExpiry <Object> valueAndExpiry = redisService.getValueAndExpiry(key);
        if (Objects.nonNull(valueAndExpiry.getValue())) {
            log.info("get data from redis:" + key);
            WxAccessToken wxAccessToken = (WxAccessToken) valueAndExpiry.getValue();
            wxAccessToken.setExpiresIn(valueAndExpiry.getExpiryTime());
            return wxAccessToken;
        }

        // Redis也没有则重新从wx服务器获取wxAccessToken
        WxAccessToken wxAccessToken = wxServerApiService.getWxAccessToken(wxAppId, wxSecret);
        log.info("get wxAccessToken from weixin server: {}", wxAccessToken);

        // 更新wxAccessToken到Redis
        redisService.set(key, wxAccessToken, wxAccessToken.getExpiresIn());

        // 发送消息到Redis频道(让其他应用把Caffeine缓存失效掉)，以达到清除脏数据的作用。
        // 这样可以确保下次访问该缓存项时，不会直接从缓存中读取，而是执行相应的方法来获取新数据并重新缓存。
        //String message = RedisMessageListener.SERVICE_ID + ":" + key;
        //redisTemplate.convertAndSend(WxConstants.REDIS_LISTENER_CHANNEL, message);

        // 更新wxAccessToken到本地Caffeine, 设置过期时间 -> 去CustomExpire配置该缓存的过期时间
        return wxAccessToken;
    }

    /**
     * Caffeine+Redis两级缓存查询-- 使用注解
     *
     * @return
     * @Cacheable注解不生效 在同一个类中，一个方法调用另外一个有注解（比如 @Cacheable，@Async，@Transational）的方法，注解是不会生效的。
     */
    @Override
    @Cacheable(value = WxConstants.WX_SESSION_CACHE_NAME, key = "#key", unless = "#result == null")
    public JSONObject getWxSessionFromCache(String key, String code) {
        // 如果caffeine没有缓存数据则先查询 Redis（2级缓存）
        RedisValueWithExpiry <Object> valueAndExpiry = redisService.getValueAndExpiry(key);
        if (Objects.nonNull(valueAndExpiry.getValue())) {
            log.info("get data from redis:" + key);
            JSONObject jsonObject = (JSONObject) valueAndExpiry.getValue();
            jsonObject.put(WxConstants.WX_EXPIRE_KEY, valueAndExpiry.getExpiryTime());
            return jsonObject;
        }

        // Redis也没有则重新从wx服务器获取wxAccessToken
        JSONObject jsonObject = wxServerApiService.jscode2session(code);
        log.info("get wxSession from weixin server: {}", jsonObject);

        // 更新到Redis
        Duration duration = Duration.ofSeconds(24 * 60 * 60);
        redisService.set(key, jsonObject, duration);

        // 设置过期时间 -> 去CustomExpire配置该缓存的过期时间
        jsonObject.put(WxConstants.WX_EXPIRE_KEY, duration.toNanos());

        // 发送消息到Redis频道(让其他应用把Caffeine缓存失效掉)，以达到清除脏数据的作用。
        // 这样可以确保下次访问该缓存项时，不会直接从缓存中读取，而是执行相应的方法来获取新数据并重新缓存。
        //String message = RedisMessageListener.SERVICE_ID + ":" + key;
        // -> 去RedisMessageListener配置该缓存的处理逻辑
        //redisTemplate.convertAndSend(WxConstants.REDIS_LISTENER_CHANNEL, message);

        // 更新到本地Caffeine, 设置过期时间 -> 去CustomExpire配置该缓存的过期时间
        return jsonObject;
    }
}
