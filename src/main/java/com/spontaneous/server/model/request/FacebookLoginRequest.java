package com.spontaneous.server.model.request;

/**
 * This class represents an HTTP POST request to login to the application.
 * Contains Facebook user id and Facebook access token.
 */
public class FacebookLoginRequest {

    /**
     * Facebook id of the user.
     */
    private final String facebookUserId;

    /**
     * Facebook token of the user.
     */
    private final String facebookToken;

    /**
     * Create a new FacebookLoginRequest
     */
    public FacebookLoginRequest(String facebookUserId, String facebookToken) {
        this.facebookUserId = facebookUserId;
        this.facebookToken = facebookToken;
    }

    public String getFacebookUserId() {
        return facebookUserId;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    @Override
    public String toString() {
        return "FacebookLoginRequest{" +
                "facebookUserId='" + facebookUserId + '\'' +
                ", facebookToken='" + facebookToken + '\'' +
                '}';
    }
}
