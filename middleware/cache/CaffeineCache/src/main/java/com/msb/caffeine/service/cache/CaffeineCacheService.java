package com.msb.caffeine.service.cache;

import com.alibaba.fastjson.JSONObject;
import com.allinpay.smartyunst.common.domain.dto.LoginMemberDTO;
import com.allinpay.smartyunst.mall.portal.domain.WxAccessToken;

/**
 * 首页内容管理Service
 * Created by macro on 2019/1/28.
 */
public interface CaffeineCacheService {

    void del(String cacheName, String key);
    String getMiniLoginTokenFromCache(String key, String openId);

    LoginMemberDTO getUmsMemberFromCache(String key, String openId);

    WxAccessToken getWxAccessTokenFromCache(String wxAppId);

    JSONObject getWxSessionFromCache(String key, String code);

}
