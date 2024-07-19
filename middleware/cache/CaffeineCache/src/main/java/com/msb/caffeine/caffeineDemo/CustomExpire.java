package com.msb.caffeine.caffeineDemo;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.allinpay.smartyunst.common.constants.WxConstants;
import com.allinpay.smartyunst.mall.portal.domain.WxAccessToken;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

/**
 * 这三个重写的方法都需要返回纳秒Nanos
 *
 * @author: kavanLi-R7000
 * @create: 2024-05-17 17:14
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public class CustomExpire implements Expiry <Object, Object> {
    //创建后(多久失效)
    @Override
    public long expireAfterCreate(Object key, Object value, long currentTime) {
        //创建后
        log.info("创建caffeine缓存, 失效计算 -- " + key + ": " + value);
        // 默认 300 秒过期
        long expireIn = TimeUnit.NANOSECONDS.convert(300, TimeUnit.SECONDS);
        expireIn = setExpireIn((String) key, value, expireIn);

        return expireIn;
    }

    //更行后(多久失效)
    @Override
    public long expireAfterUpdate(Object key, Object value, long currentTime, long currentDuration) {
        //更新后
        log.info("更新caffeine缓存, 失效计算 -- " + key + ": " + value);
        // 默认 300 秒过期
        long expireIn = TimeUnit.NANOSECONDS.convert(300, TimeUnit.SECONDS);
        expireIn = setExpireIn((String) key, value, expireIn);

        return expireIn;
    }

    //读取后(多久失效)
    @Override
    public long expireAfterRead(Object key, Object value, long currentTime, long currentDuration) {
        // 保持原来的失效时间
        return currentDuration;
    }

    @Nullable
    private static Long setExpireIn(String key, Object value, long expireIn) {
        // 根据键或值来动态设置过期时间
        if (key.startsWith(WxConstants.WX_TOKEN_KEY)) {
            WxAccessToken wxAccessToken = (WxAccessToken) value;
            // 特殊条目默认 7200 秒过期
            expireIn = TimeUnit.SECONDS.toNanos(wxAccessToken.getExpiresIn());
        }
        if (key.startsWith(WxConstants.WX_SESSION_CACHE_NAME)) {
            JSONObject jsonObject = (JSONObject) value;
            expireIn = TimeUnit.SECONDS.toNanos((Long) jsonObject.get(WxConstants.WX_EXPIRE_KEY));
        }
        if (key.startsWith(WxConstants.UMS_MEMBER_KEY)) {
            LoginMemberDTO loginMemberDTO = (LoginMemberDTO) value;
            // 5天过期
            expireIn = TimeUnit.SECONDS.toNanos(loginMemberDTO.getExpireIn());
        }
        // 将纳秒转换为小时
        double expireInHours = (double) expireIn / TimeUnit.HOURS.toNanos(1);
        log.info("失效时间（小时）: " + expireInHours);
        return expireIn;
    }

}
