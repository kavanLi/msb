package com.msb.caffeine.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.annotation.PostConstruct;

import com.github.benmanes.caffeine.cache.Cache;
import com.msb.caffeine.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 首先需要在Redis配置文件中打开键空间通知功能,设置`notify-keyspace-events`选项:
 *
 * notify-keyspace-events Ex
 */
@Slf4j
@Component
public class RedisMessageListener  implements MessageListener {


    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 这个 SERVICE_ID 包含了应用名称、启动时间和随机字符串。可以在消息中包含这个 SERVICE_ID，并在接收消息时进行过滤，从而避免处理自己发送的消息。
     */
    public static String SERVICE_ID = "your-service-id";

    @Autowired
    private Cache cache;

    @Autowired
    @Qualifier("caffeineCacheManager")
    private CacheManager cacheManager;


    //这里就是应用接收到了（要删除缓存的策略）： 这里就强制删除Caffeine中的缓存数据
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageBody = new String(message.getBody());
        if (messageBody.startsWith(SERVICE_ID)) {
            // 忽略自己发送的消息
            return;
        }

        // 使用 Hutool 的字符串处理方法 如果字符串不包含指定的分隔符 :, 这个方法会返回原字符串。
        String cacheKey = StrUtil.subAfter(messageBody, ":", true);
        if(!cacheKey.equals("")){
            log.info("invalidate:"+cacheKey);
            //cacheManager.getCache("cacheName").evict(cacheKey);
            cache.invalidate(cacheKey);
        }
    }

    @PostConstruct
    private void init() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomString = generateRandomString(6);
        this.SERVICE_ID = applicationName + "-" + timestamp + "-" + randomString;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
