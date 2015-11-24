package com.spontaneous.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * A class for configuring the Facebook app credentials.
 */
@Component
@ConfigurationProperties(prefix = "spring.social.facebook")
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

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
