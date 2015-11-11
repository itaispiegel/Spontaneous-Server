package com.spontaneous.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.spontaneous.server.config.FacebookConf;
import org.hibernate.service.spi.ServiceException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ApiException;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * Created by Eidan on 4/25/2015.
 */
@Service
public class FacebookService {

    @Autowired
    private FacebookConf mFacebookConf;

    private MyFacebookTemplate getFacebookTemplate(String accessToken) {
        return new MyFacebookTemplate(accessToken);
    }

    public String getPictureUrl(String accessToken, String userId, ImageType imageType) {
        return getFacebookTemplate(accessToken).fetchPictureUrl(userId, imageType);
    }

    public String getUserEmail(String accessToken, String userId) throws ServiceException {
        try {
            return getFacebookTemplate(accessToken).userOperations().getUserProfile(userId).getEmail();
        } catch(ApiException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public String getFullName(String accessToken, String userId) throws ServiceException {
        try {
            User user = getFacebookTemplate(accessToken).userOperations().getUserProfile(userId);
            return user.getFirstName()+ " " + user.getLastName();
        } catch(ApiException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public DateTime getUserBirthday(String accessToken, String userId) throws ServiceException {
        try {
            User user = getFacebookTemplate(accessToken).userOperations().getUserProfile(userId);
            return DateTime.parse(user.getBirthday());
        } catch (ApiException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public void getUserDetails(com.spontaneous.server.model.entity.User user,
                                                                   String accessToken, String facebookUserId) {
        user.setProfilePicture(getPictureUrl(accessToken, facebookUserId, ImageType.LARGE));
        user.setEmail(getUserEmail(accessToken, facebookUserId));
        user.setName(getFullName(accessToken, facebookUserId));
        user.setBirthday(getUserBirthday(accessToken, facebookUserId));
    }

    public class MyFacebookTemplate extends FacebookTemplate {

        public MyFacebookTemplate(String accessToken) {
            super(accessToken);
        }

        public MyFacebookTemplate(String accessToken, String applicationNamespace) {
            super(accessToken, applicationNamespace);
        }

        public MyFacebookTemplate(String accessToken, String applicationNamespace, String appId) {
            super(accessToken, applicationNamespace, appId);
        }

        public String fetchPictureUrl(String userId, ImageType imageType) {
            URI uri = URIBuilder.fromUri(GRAPH_API_URL + userId + "/picture" +
                    "?type=" + imageType.toString().toLowerCase() + "&redirect=false").build();

            JsonNode response = getRestTemplate().getForObject(uri, JsonNode.class);
            return response.get("data").get("url").asText();
        }
    }

}
