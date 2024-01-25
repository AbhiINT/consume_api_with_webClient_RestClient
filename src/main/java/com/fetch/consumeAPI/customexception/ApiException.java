package com.fetch.consumeAPI.customexception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ApiException {

    private String message;

    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;
    private String ErrorDetails;
    private String support;

    public ApiException(String message, String ErrorDetails, String support, HttpStatus httpStatus,
            ZonedDateTime zonedDateTime) {
        this.message = message;
        this.ErrorDetails = ErrorDetails;
        this.support = support;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public String getErrorDetails() {
        return ErrorDetails;
    }

    public String getSupport() {
        return support;
    }

}
