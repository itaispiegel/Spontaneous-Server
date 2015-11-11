package com.spontaneous.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Facebook configuration class.
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
