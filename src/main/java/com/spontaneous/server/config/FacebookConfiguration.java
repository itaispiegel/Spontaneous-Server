package com.spontaneous.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A class for configuring the Facebook app credentials.
 */
@Component
public class FacebookConfiguration {

    private String appId;
    private String appSecret;

    @Autowired
    public FacebookConfiguration(@Value("${spring.social.facebook.app-id}") String appId,
                                 @Value("${spring.social.facebook.app-secret}") String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
