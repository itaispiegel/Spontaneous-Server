package com.example.model.request;

/**
 * Created by USER1 on 09/11/2015.
 */
public class LoginRequest {

    private String facebookUserId;

    private String facebookToken;

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
        return "LoginRequest{" +
                "facebookUserId='" + facebookUserId + '\'' +
                ", facebookToken='" + facebookToken + '\'' +
                '}';
    }
}
