package com.example.demo.service.retry;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSONObject;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * 首页内容管理Service实现类
 * Created by macro on 2019/1/28.
 */
@Service
@Slf4j
public class WxServerApiServiceImpl implements WxServerApiService {

    //@Value("${oa.wx.appId}")
    //private String wxAppId;
    //
    //@Value("${oa.wx.secret}")
    //private String wxSecret;

    @Autowired
    private OkHttpClient readOkHttpClient;

    @Autowired
    private OkHttpClient writeOkHttpClient;

    //@Autowired
    //private OkHttp3Client okHttp3Client;
    //
    //@Autowired
    //private CaffeineCacheService caffeineCacheService;
    //
    //@Autowired
    //private RedisService redisService;
    //
    //@Autowired
    //@Qualifier("caffeineCacheManager")
    //private CacheManager caffeineCacheManager;
    //
    //@Value("${wx.api.code2session}")
    //private String code2sessionUrl;
    //
    //@Value("${wx.api.getPhoneNumber}")
    //private String getPhoneNumberUrl;
    //
    //@Value("${wx.api.getAccessToken}")
    //private String getAccessTokenUrl;
    //
    //
    ///**
    // * 重试方法的实现
    // * @param code
    // * @return
    // */
    //@Override
    //public JSONObject getWxPhoneNumberWithRetry(String code) {
    //    log.info("获取手机号 ***start***");
    //    String key = WxConstants.WX_TOKEN_KEY + wxAppId;
    //
    //    // 配置重试策略
    //    RetryConfig config = RetryConfig.custom()
    //            .maxAttempts(2)
    //            .waitDuration(Duration.ofSeconds(3))
    //            .retryExceptions(SmartyunstException.class)
    //            .build();
    //
    //    Retry retry = Retry.of("wxPhoneNumberRetry", config);
    //
    //    // 定义需要重试的操作
    //    Supplier <JSONObject> retryableSupplier = Retry.decorateSupplier(retry, () -> {
    //        // 获取缓存中的 access token
    //        WxAccessToken wxAccessTokenFromCache = caffeineCacheService.getWxAccessTokenFromCache(key);
    //        String[] paramNames = {"access_token"};
    //        String[] paramValues = {wxAccessTokenFromCache.getWxAccessToken()};
    //
    //        String[] jsonBodyNames = {"code"};
    //        String[] jsonBodyValues = {code};
    //
    //        // 发送请求
    //        String responseBody = okHttp3Client.okhttpPost(getPhoneNumberUrl, readOkHttpClient,
    //                OkHttp3Client.buildJsonBodyString(jsonBodyNames, jsonBodyValues),
    //                OkHttp3Client.buildQueryParamsMap(paramNames, paramValues));
    //
    //        log.info("获取手机号 ***end***");
    //
    //        // 解析响应
    //        JSONObject jsonObject = JSONObject.parseObject(responseBody);
    //        if (StringUtils.contains(responseBody, "errcode") &&
    //                !StringUtils.equalsIgnoreCase(jsonObject.getString("errcode"), "0")) {
    //            if (Stream.of("invalid", "credential", "expired", "not latest").anyMatch(e -> StringUtils.contains(responseBody, e))) {
    //                // 删除缓存中的 token
    //                caffeineCacheService.del(WxConstants.WX_TOKEN_CACHE_NAME, key);
    //                redisService.del(key);
    //                // 抛出自定义异常以触发重试
    //                throw new SmartyunstException(ErrorCodeEnum.FAIL_C, "Token error, will retry");
    //            }
    //            throw new SmartyunstException(ErrorCodeEnum.FAIL_C, ErrorCodeEnum.FAIL_C.getValue() + "phoneCode -> " + jsonObject.getString("errmsg") + "，errcode -> " + jsonObject.getString("errcode"));
    //        }
    //
    //        // 如果没有错误，返回结果
    //        return jsonObject;
    //    });
    //
    //    // 使用 Try 执行重试操作
    //    JSONObject result = Try.ofSupplier(retryableSupplier)
    //            .recover(throwable -> {
    //                log.error("重试后仍然失败", throwable);
    //                if (throwable instanceof SmartyunstException) {
    //                    throw (SmartyunstException) throwable;
    //                } else {
    //                    throw new RuntimeException(throwable);
    //                }
    //            }).get();
    //
    //    return result;
    //
    //    // 下面的注释是原始代码，没有重试策略，留用，如果上面的代码执行没有异常可以考虑删除注释
    //
    //    //log.info("获取手机号 ***start***");
    //    //// 组装参数名和参数值
    //    //// "wxAccessToken-xxxxx"
    //    //String key = WxConstants.WX_TOKEN_KEY + wxAppId;
    //    //WxAccessToken wxAccessTokenFromCache = caffeineCacheService.getWxAccessTokenFromCache(key);
    //    //String[] paramNames = {"access_token"};
    //    //String[] paramValues = {wxAccessTokenFromCache.getWxAccessToken()};
    //    //
    //    //String[] jsonBodyNames = {"code"};
    //    //String[] jsonBodyValues = {code};
    //    //
    //    //String responseBody = okHttp3Client.okhttpPost(getPhoneNumberUrl, readOkHttpClient,
    //    //        OkHttp3Client.buildJsonBodyString(jsonBodyNames, jsonBodyValues),
    //    //        OkHttp3Client.buildQueryParamsMap(paramNames, paramValues));
    //    //log.info("获取手机号 ***end***");
    //    //
    //    //JSONObject jsonObject = JSONObject.parseObject(responseBody);
    //    //if (StringUtils.contains(responseBody, "errcode") &&
    //    //        !StringUtils.equalsIgnoreCase(jsonObject.getString("errcode"), "0")) {
    //    //    if (Stream.of("invalid", "credential", "expired", "not latest").anyMatch(e -> StringUtils.contains(responseBody, e))) {
    //    //        caffeineCacheService.del(WxConstants.WX_TOKEN_CACHE_NAME, key);
    //    //        redisService.del(key);
    //    //    }
    //    //    throw new SmartyunstException(ErrorCodeEnum.FAIL_C, ErrorCodeEnum.FAIL_C.getValue() + "phoneCode -> " + jsonObject.getString("errmsg") + "，errcode -> " + jsonObject.getString("errcode"));
    //    //}
    //    //
    //    //return jsonObject;
    //}
    //
    //public JSONObject jscode2session(String code) {
    //    log.info("登录凭证校验接口 ***start***");
    //    // 将appid，appSecret（密钥）和code发送给微信接口服务去校验登录凭证，成功后会返回session_key(会话信息记录)和openid(用户唯一标识)
    //
    //    // 一组参数名和参数值
    //    String[] paramNames = {"appid", "secret", "js_code", "grant_type"};
    //    String[] paramValues = {wxAppId, wxSecret, code, WxConstants.GRANT_TYPE};
    //
    //    String responseBody = okHttp3Client.okhttpGet(code2sessionUrl, readOkHttpClient, OkHttp3Client.buildQueryParamsMap(paramNames, paramValues));
    //    log.info("登录凭证校验接口 ***end***");
    //
    //    // 校验登录凭证成功后，将openid和session_key保存，生成一个自定义登录态的token(令牌)响应回去给前端。
    //    JSONObject jsonObject = JSONObject.parseObject(responseBody);
    //    if (StringUtils.contains(responseBody, "errcode") &&
    //            !StringUtils.equalsIgnoreCase(jsonObject.getString("errcode"), "0")) {
    //        throw new SmartyunstException(ErrorCodeEnum.FAIL_C, ErrorCodeEnum.FAIL_C.getValue() + "loginCode -> " + jsonObject.getString("errmsg") + "，errcode -> " + jsonObject.getString("errcode"));
    //    }
    //    return jsonObject;
    //}
    //
    //@Override
    //public WxAccessToken getWxAccessToken(String appid, String secret) {
    //    log.info("获取接口调用凭据 ***start***");
    //    // 一组参数名和参数值
    //    String[] paramNames = {"appid", "secret", "grant_type"};
    //    String[] paramValues = {wxAppId, wxSecret, WxConstants.ACCESS_TOKEN_GRANT_TYPE};
    //
    //    String responseBody = okHttp3Client.okhttpGet(getAccessTokenUrl, readOkHttpClient, OkHttp3Client.buildQueryParamsMap(paramNames, paramValues));
    //    log.info("获取接口调用凭据 ***end***");
    //
    //    JSONObject jsonObject = JSONObject.parseObject(responseBody);
    //    if (StringUtils.contains(responseBody, "errcode") &&
    //            !StringUtils.equalsIgnoreCase(jsonObject.getString("errcode"), "0")) {
    //        throw new SmartyunstException(ErrorCodeEnum.FAIL_C, ErrorCodeEnum.FAIL_C.getValue() + jsonObject.getString("errmsg") + "，errcode -> " + jsonObject.getString("errcode"));
    //    }
    //
    //    String wxAccessToken = jsonObject.getString("access_token");
    //    Long expiresIn = Long.valueOf(jsonObject.getString("expires_in"));
    //    return new WxAccessToken().setWxAccessToken(wxAccessToken).setExpiresIn(expiresIn);
    //}





}
