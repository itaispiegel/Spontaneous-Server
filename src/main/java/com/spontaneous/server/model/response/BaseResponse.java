package com.spontaneous.server.model.response;

/**
 * This class holds data returned from the REST API.
 */
public class BaseResponse<T> {

    /**
     * Status code of the HTTP Request.
     */
    private final int statusCode;

    /**
     * The data itself.
     */
    private final T body;

    /**
     * Description of the error (optional).
     */
    private String description;

    // -----Response codes-----

    /**
     * Request was success.
     */
    public static final int SUCCESS = 0;

    /**
     * Request ended with error.
     */
    public static final int INTERNAL_ERROR = -1;
    //----------

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
    public BaseResponse(int statusCode, String description, T body) {
        this.statusCode = statusCode;
        this.description = description;
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
    public Object getBody() {
        return body;
    }

    /**
     * @return Description of the error (optional).
     */
    public String getDescription() {
        return description;
    }
}
