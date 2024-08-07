package com.example.demo.config.property;

import java.util.List;
import java.util.Map;

import com.example.demo.config.property.url.UrlConfig4Yunst2;
import lombok.Data;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-23 15:25
 * To change this template use File | Settings | File and Code Templates.
 */
@Data
public class AppIdConfig {
    private String channelType; // 渠道类型 1-云商通2.0参数  2-SYB参数  3-微信参数 对应枚举 PARAM_CHANNEL_TYPE
    private String url;
    private String merchantTag; // API("1", "集团API版"), SAAS("0", "集团saas版"); 对应枚举 MerchantTag
    private Integer sensitiveEncryptTag; // 敏感数据加密密钥类型 对应枚举 EncryptType
    private String appId;
    private String spAppId;
    private String secretKey;
    private String privateKey;
    private String allinpayPublicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE/VKHBem28IXD30yuZN1QcNgGE4gzqgd" +
            "/eX1ZEouUleLNfrnQJkOs7LzAag3q10uaH/e9+5JyJDx3ULfKS4QZPw=="; // 默认值
    /**
     *  ObjectMapper mapper = new ObjectMapper();
     *  // Serialize UrlConfig object to JSON string
     *  mapper.writeValueAsString(urlConfig)
     *  // Deserialize JSON string to UrlConfig object
     *  UrlConfig urlConfig = mapper.readValue(jsonString, UrlConfig.class);
     */
    private UrlConfig4Yunst2 urlConfig;
}
