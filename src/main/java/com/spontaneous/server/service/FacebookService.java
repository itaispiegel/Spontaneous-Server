package com.spontaneous.server.service;

import com.fasterxml.jackson.databind.JsonNode;
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
public class FacebookService {

    /**
     * The namespace of the application.
     */
    private static final String APP_NAMESPACE = "Spontaneous";

    /**
     * Get the Facebook instance. implements the singleton pattern.
     */
    private static FacebookTemplate getFacebookTemplate(String accessToken) {
        return new FacebookTemplate(accessToken, APP_NAMESPACE);
    }

    /**
     * Get user by user id and access token.
     */
    private User getUser(String accessToken, String userId) throws ServiceException {
        return getFacebookTemplate(accessToken)
                .userOperations()
                .getUserProfile(userId);
    }

    /**
     *
     * @param accessToken Facebook access token of the user.
     * @param userId Facebook id of the user.
     * @param imageType The type of the image.
     * @return Facebook profile picture URL.
     */
    private String fetchProfilePictureURL(String accessToken, String userId, ImageType imageType) {
        //Build the URL of the HTTP request.
        URI uri = URIBuilder.fromUri(getFacebookTemplate(accessToken).getBaseGraphApiUrl() + userId + "/picture" +
                "?type=" + imageType.toString().toLowerCase() + "&redirect=false").build();

        //Send an HTTP request and get the URL.
        JsonNode response = getFacebookTemplate(accessToken).getRestTemplate().getForObject(uri, JsonNode.class);
        return response.get("data").get("url").textValue();
    }

    /**
     * Set user details by user id and access token.
     */
    public com.spontaneous.server.model.entity.User setUserDetails(com.spontaneous.server.model.entity.User user,
                                                                   String accessToken, String facebookUserId) {

        //Get the user details from Facebook.
        User facebookUser = getUser(accessToken, facebookUserId);

        user.setProfilePicture(fetchProfilePictureURL(accessToken, facebookUserId, ImageType.LARGE));

        user.setEmail(facebookUser.getEmail());
        user.setName(facebookUser.getName());
        user.setBirthday(facebookUser.getBirthday());
        user.setGender(facebookUser.getGender());

        return user;
    }
}
