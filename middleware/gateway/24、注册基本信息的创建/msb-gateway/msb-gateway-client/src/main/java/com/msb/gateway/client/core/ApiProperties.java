package com.msb.gateway.client.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;

@Data
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private String registerAddress;

    private String env = "dev";

    private boolean gray;
}
