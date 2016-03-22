package com.spontaneous.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.ApiException;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Reference;
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

    private static final String APP_NAMESPACE = "Spontaneous";
    private final Logger mLogger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param accessToken Facebook auth access token of the user.
     * @return Instance of {@link FacebookTemplate} object, for making HTTP requests to the Facebook Graph API.
     */
    private FacebookTemplate getFacebookTemplate(String accessToken) {
        return new FacebookTemplate(accessToken, APP_NAMESPACE);
    }

    /**
     * Get user details by his Facebook id and access token.
     *
     * @param accessToken Facebook access token of the user.
     * @return User details.
     * @throws ApiException Is caught in case there was no user found with the given access credentials.
     */
    private User getUserProfile(String accessToken) throws ApiException {

        try {
            return getFacebookTemplate(accessToken)
                    .userOperations()
                    .getUserProfile();
        } catch (ApiException e) {
            mLogger.error("Facebook access token or user id are incorrect.");
            mLogger.error(e.getMessage());

            throw e;
        }
    }

    /**
     * Get the Facebook friends of the authenticated user.
     * @param accessToken Access token of the authenticated user.
     * @return List of friends.
     * @throws ApiException In case of a Facebook error.
     */
    public PagedList<Reference> getUserFriends(String accessToken) throws ApiException {
        return getFacebookTemplate(accessToken)
                .friendOperations()
                .getFriends();
    }

    /**
     * Get a profile picture url given the user id.
     * <br/>NOTICE: The default image size is {@link ImageType#LARGE}.
     *
     * @param accessToken Facebook access token of the user.
     * @param userId      Facebook id of the user.
     * @return Facebook profile picture URL.
     */
    private String fetchProfilePictureUrl(String accessToken, String userId) {

        FacebookTemplate facebookTemplate = getFacebookTemplate(accessToken);

        //Build the URL of the HTTP request.
        URI uri = URIBuilder.fromUri(facebookTemplate.getBaseGraphApiUrl() + userId + "/picture" +
                "?type=" + ImageType.LARGE.toString().toLowerCase() + "&redirect=false").build();

        //Send an HTTP request and get the URL.
        JsonNode response = facebookTemplate.getRestTemplate().getForObject(uri, JsonNode.class);
        return response.get("data").get("url").textValue();
    }

    /**
     * Set user details by user id and access token.
     *
     * @param user           Instance of the user entity..
     * @param accessToken    Facebook auth access token.
     * @param facebookUserId Facebook id of the user.
     * @return User entity with full details.
     * @throws ApiException Is caught in case there was no user found with the given access credentials
     */
    public com.spontaneous.server.model.entity.User setUserDetails(com.spontaneous.server.model.entity.User user,
                                                                   String accessToken, String facebookUserId) throws ApiException, IllegalArgumentException {

        //Get the user details from Facebook.
        User facebookUser = getUserProfile(accessToken);

        user.setFacebookToken(accessToken);
        user.setFacebookUserId(facebookUserId);

        user.setProfilePicture(fetchProfilePictureUrl(accessToken, facebookUserId));

        user.setEmail(facebookUser.getEmail());
        user.setName(facebookUser.getName());
        user.setBirthday(facebookUser.getBirthday(), "MM/dd/yyyy");
        user.setGender(facebookUser.getGender());

        return user;
    }
}
