package com.subaxpress.lib.shared;

public class RestApiServerException extends Exception {
    private String requestUrl;
    private String message;
    public RestApiServerException(String requestUrl, String message) {
        super(message);
    }
}
