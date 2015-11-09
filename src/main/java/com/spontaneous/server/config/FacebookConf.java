package com.spontaneous.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by USER1 on 09/11/2015.
 */
@Component
@ConfigurationProperties(prefix = "spring.facebook")
public class FacebookConf {

    private String appId;
    private String appSecret;

    public FacebookConf() {
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
