package com.spontaneous.server.service;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * This class is part of the service layer of the application and is used for Facebook integration with the application.
 */
@Service
public class FacebookService {

    private static Facebook facebook;

    private static final String APP_ID = "1643579359242605";
    private static final String APP_SECRET = "fae2da42dc4b79b81a3dd72d4e2a763b";

    private static Facebook getInstance(String accessToken) {

        if(facebook == null) {
            ConfigurationBuilder cb = new ConfigurationBuilder()
                    .setOAuthAppSecret(APP_SECRET)
                    .setOAuthAppId(APP_ID)
                    .setOAuthAccessToken(accessToken);

            facebook = new FacebookFactory(cb.build()).getInstance();
        }

        return facebook;
    }

    public String getUserEmail(String accessToken, String userId) throws ServiceException {
        try {
            User user = getInstance(accessToken).getUser(userId, new Reading().fields("email"));
            return user.getEmail();
        } catch (FacebookException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public String getFullName(String accessToken, String userId) throws ServiceException {
        try {
            User user = getInstance(accessToken).getUser(userId, new Reading().fields("name"));
            return user.getName();
        } catch (FacebookException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public String getUserBirthday(String accessToken, String userId) throws ServiceException {
        try {
            User user = getInstance(accessToken).getUser(userId, new Reading().fields("birthday"));
            return user.getBirthday();
        } catch (FacebookException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public URL fetchPictureUrl(String accessToken, String userId) {
        try {
            return getInstance(accessToken).getPictureURL(userId, PictureSize.large);
        } catch (FacebookException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public com.spontaneous.server.model.entity.User getUserDetails(com.spontaneous.server.model.entity.User user,
                                                                   String accessToken, String facebookUserId) {

        user.setProfilePicture(fetchPictureUrl(accessToken, facebookUserId));
        user.setEmail(getUserEmail(accessToken, facebookUserId));
        user.setName(getFullName(accessToken, facebookUserId));
        user.setBirthday(getUserBirthday(accessToken, facebookUserId));

        return user;
    }

}
