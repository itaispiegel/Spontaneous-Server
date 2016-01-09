package com.spontaneous.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.spontaneous.server.config.BaseComponent;
import org.hibernate.service.spi.ServiceException;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * This class is part of the service layer of the application and is used for Facebook integration with the application.
 */
@Service
public class FacebookService extends BaseComponent {

    /**
     * The namespace of the application.
     */
    private static final String APP_NAMESPACE = "Spontaneous";

    /**
     * Facebook methods.
     */
    private static MyFacebookTemplate facebook;

    /**
     * Get the Facebook instance. implements the singleton pattern.
     */
    private static synchronized MyFacebookTemplate getInstance(String accessToken) {

        if (facebook == null) {
            facebook = new MyFacebookTemplate(accessToken, APP_NAMESPACE);
        }

        return facebook;
    }

    /**
     * Get user by user id and access token.
     */
    private User getUser(String accessToken, String userId) throws ServiceException {
        return getInstance(accessToken)
                .userOperations()
                .getUserProfile(userId);
    }

    /**
     * Set user details by user id and access token.
     */
    public com.spontaneous.server.model.entity.User setUserDetails(com.spontaneous.server.model.entity.User user,
                                                                   String accessToken, String facebookUserId) {

        //Get the user details from Facebook.
        User facebookUser = getUser(accessToken, facebookUserId);

        user.setProfilePicture(getInstance(accessToken)
                .fetchPictureUrl(facebookUserId, ImageType.LARGE));

        user.setEmail(facebookUser.getEmail());
        user.setName(facebookUser.getName());
        user.setBirthday(facebookUser.getBirthday());
        user.setGender(facebookUser.getGender());

        return user;
    }

    /**
     * The custom FacebookTemplate adds the {@link #fetchPictureUrl(String, ImageType)} method.
     */
    static class MyFacebookTemplate extends FacebookTemplate {

        public MyFacebookTemplate(String accessToken, String applicationNamespace) {
            super(accessToken, applicationNamespace);
        }

        /**
         * @param userId    Id of the user.
         * @param imageType {@link ImageType} is the size of the image.
         * @return Facebook profile picture URL.
         */
        public String fetchPictureUrl(String userId, ImageType imageType) {

            //Build the URL of the HTTP request.
            URI uri = URIBuilder.fromUri(getBaseGraphApiUrl() + userId + "/picture" +
                    "?type=" + imageType.toString().toLowerCase() + "&redirect=false").build();

            //Send an HTTP request and get the URL.
            JsonNode response = getRestTemplate().getForObject(uri, JsonNode.class);
            return response.get("data").get("url").textValue();
        }
    }
}
