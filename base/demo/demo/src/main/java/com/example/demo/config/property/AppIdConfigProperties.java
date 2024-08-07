package com.example.demo.config.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-23 14:46
 * To change this template use File | Settings | File and Code Templates.
 */
@Component
@ConfigurationProperties(prefix = "channel.yunst2.config-list")
public class AppIdConfigProperties {

    private List <AppIdConfig> appIdConfigs;

    public void setApp(List <AppIdConfig> appIdConfigs) {
        this.appIdConfigs = appIdConfigs;
    }

    public List <AppIdConfig> getApp() {
        return appIdConfigs;
    }

}
