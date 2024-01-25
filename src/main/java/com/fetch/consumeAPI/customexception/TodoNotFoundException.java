package com.fetch.consumeAPI.customexception;

import org.springframework.http.HttpStatus;

public class TodoNotFoundException extends RuntimeException {
    String ErrorDetails;
    HttpStatus http;

    public String getErrorDetails() {
        return ErrorDetails;
    }

    public TodoNotFoundException(String msg, String errorDetails, HttpStatus http) {
        super(msg);
        this.ErrorDetails = errorDetails;
        this.http = http;
    }

    public TodoNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0];
    }
}
