package com.fetch.consumeAPI.customexception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandeler {
    @ExceptionHandler(value = { TodoNotFoundException.class })
    public ResponseEntity<Object> handleApiRequestException(TodoNotFoundException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), ex.ErrorDetails,
                "For Help please visit https://www.help.com", ex.http, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}