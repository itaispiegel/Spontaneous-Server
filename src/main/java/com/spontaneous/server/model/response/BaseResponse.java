package com.spontaneous.server.model.response;

import com.google.gson.annotations.Expose;

/**
 * This class holds data returned from the REST API.
 */
public class BaseResponse<T> {

    /**
     * Status code of the HTTP Request.
     */
    @Expose
    private final int statusCode;

    /**
     * The data itself.
     */
    @Expose
    private final T body;

    /**
     * Request was success.
     */
    public static final int SUCCESS = 0;

    /**
     * Request ended with error.
     */
    public static final int INTERNAL_ERROR = -1;

    /**
     * Create a response with a body. Default response code is ResponseCodes.SUCCESS.
     */
    public BaseResponse(T body) {
        this.statusCode = SUCCESS;
        this.body = body;
    }

    /**
     * Create a response code with status code, description and body.
     */
    public BaseResponse(int statusCode, T body) {
        this.statusCode = BaseResponse.INTERNAL_ERROR;
        this.body = body;
    }

    /**
     * @return Status code of the HTTP Request.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return The data itself.
     */
    public T getBody() {
        return body;
    }
}
