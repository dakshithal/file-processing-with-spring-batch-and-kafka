package com.dbs.exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    /**
     * Catch all exceptions of this controller using adviser and returns standard response
     * @param ex Exception caught by adviser
     * @return Standard http response with status and reason
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> responseEntity(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
