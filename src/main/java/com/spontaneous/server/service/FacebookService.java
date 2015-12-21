package com.spontaneous.server.service;

import com.spontaneous.server.config.FacebookConfiguration;
import facebook4j.*;
import facebook4j.conf.ConfigurationBuilder;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is part of the service layer of the application and is used for Facebook integration with the application.
 */
@Service
public class FacebookService {

    /**
     * Facebook configuration class.
     * Contains app secret and app id.
     */
    @Autowired
    private FacebookConfiguration mFacebookConf;

    /**
     * Facebook methods.
     */
    private static Facebook facebook;

    /**
     * User fields to receive.
     */
    private static final String[] USER_FIELDS = {
            "email", "birthday", "name", "gender", "picture"
    };

    /**
     * Get the Facebook instance. implements the singleton pattern.
     */
    private Facebook getInstance(String accessToken) {

        if (facebook == null) {
            ConfigurationBuilder cb = new ConfigurationBuilder()
                    .setOAuthAppSecret(mFacebookConf.getAppSecret())
                    .setOAuthAppId(mFacebookConf.getAppId())
                    .setOAuthAccessToken(accessToken);

            facebook = new FacebookFactory(cb.build()).getInstance();
        }

        return facebook;
    }

    /**
     * Get user by user id and access token.
     */
    private User getUser(String accessToken, String userId) throws ServiceException {
        try {
            return getInstance(accessToken)
                    .getUser(userId, new Reading().fields(USER_FIELDS));
        } catch (FacebookException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Get user profile picture by user id and access token.
     */
    public String getProfilePicture(String accessToken, String userId) {
        try {
            return getInstance(accessToken)
                    .getPictureURL(userId, PictureSize.large)
                    .toString();

        } catch (FacebookException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Set user details by user id and access token.
     */
    public com.spontaneous.server.model.entity.User setUserDetails(com.spontaneous.server.model.entity.User user,
                                                                   String accessToken, String facebookUserId) {

        User facebookUser = getUser(accessToken, facebookUserId);

        user.setProfilePicture(getProfilePicture(accessToken, facebookUserId));
        user.setEmail(facebookUser.getEmail());
        user.setName(facebookUser.getName());
        user.setBirthday(facebookUser.getBirthday());
        user.setGender(facebookUser.getGender());

        return user;
    }
}
