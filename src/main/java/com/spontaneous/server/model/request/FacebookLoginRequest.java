package com.spontaneous.server.model.request;

/**
 * This class represents an HTTP POST request to login to the application.
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

    private FacebookLoginRequest(Builder builder) {
        facebookUserId = builder.facebookUserId;
        facebookToken = builder.facebookToken;
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


    /**
     * {@code FacebookLoginRequest} builder static inner class.
     */
    public static final class Builder {
        private String facebookUserId;
        private String facebookToken;

        public Builder() {
        }

        /**
         * Sets the {@code facebookUserId} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code facebookUserId} to set
         * @return a reference to this Builder
         */
        public Builder facebookUserId(String val) {
            facebookUserId = val;
            return this;
        }

        /**
         * Sets the {@code facebookToken} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code facebookToken} to set
         * @return a reference to this Builder
         */
        public Builder facebookToken(String val) {
            facebookToken = val;
            return this;
        }

        /**
         * Returns a {@code FacebookLoginRequest} built from the parameters previously set.
         *
         * @return a {@code FacebookLoginRequest} built with parameters of this {@code FacebookLoginRequest.Builder}
         */
        public FacebookLoginRequest build() {
            return new FacebookLoginRequest(this);
        }
    }
}
