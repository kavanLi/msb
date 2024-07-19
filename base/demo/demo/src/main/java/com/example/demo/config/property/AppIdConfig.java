package com.example.demo.config.property;

import lombok.Data;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-23 15:25
 * To change this template use File | Settings | File and Code Templates.
 */
@Data
public class AppIdConfig {
    private String url;
    private String appId;
    private String secretKey;
    private String privateKey;
    private String allinpayPublicKey;
}
