package com.spontaneous.server.model.response;

/**
 * Response codes to be returned from REST API.
 */
public final class ResponseCodes {

    /**
     * Request was success.
     */
    public static final int SUCCESS = 0;

    /**
     * Request ended with error.
     */
    public static final int INTERNAL_ERROR = -1;

    private ResponseCodes() {
    }
}
