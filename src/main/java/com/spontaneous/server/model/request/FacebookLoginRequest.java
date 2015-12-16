package com.spontaneous.server.model.request;

/**
 * This class represents an HTTP JSON request to login to the application.
 * Contains Facebook user id and Facebook access token.
 */
public class FacebookLoginRequest {

    /**
     * Facebook id of the user.
     */
    private String facebookUserId;

    /**
     * Facebook token of the user.
     */
    private String facebookToken;

    /**
     * Create a new request.
     */
    public FacebookLoginRequest() {
    }

    public String getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(String facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    @Override
    public String toString() {
        return "FacebookLoginRequest{" +
                "facebookUserId='" + facebookUserId + '\'' +
                ", facebookToken='" + facebookToken + '\'' +
                '}';
    }
}
