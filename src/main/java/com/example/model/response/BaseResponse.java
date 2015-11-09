package com.example.model.response;

/**
 * Created by Eidan on 12/26/2014.
 */
public class BaseResponse<T> {

    private int statusCode;
    private String description;
    private T body;

    public BaseResponse() {
    }

    public BaseResponse(int statusCode, T body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public BaseResponse(int statusCode, String description, T body) {
        this.statusCode = statusCode;
        this.description = description;
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
