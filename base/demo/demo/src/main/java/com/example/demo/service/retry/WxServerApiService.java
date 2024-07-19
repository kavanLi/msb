package com.example.demo.service.retry;

import com.alibaba.fastjson.JSONObject;

/**
 * 首页内容管理Service
 * Created by macro on 2019/1/28.
 */
public interface WxServerApiService {



    /**
     * https://api.weixin.qq.com/sns/jscode2session
     * @param code
     * @return
     */
    //JSONObject jscode2session(String code);

    /**
     * https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getAccessToken.html
     *
     * @param appid
     * @param secret
     */
    //WxAccessToken getWxAccessToken(String appid, String secret);

    /**
     * https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
     *
     * @param code
     */
    //JSONObject getWxPhoneNumberWithRetry(String code);

}
