package com.study.exception.exception;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class StudyExceptionHandler {
    @ExceptionHandler(value = {TestException.class})
    public ResponseEntity<Object> testException(TestException exception, HttpServletRequest request){
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.TEST_EXCEPTION, request);
        return new ResponseEntity<>(response, ExceptionCode.TEST_EXCEPTION.getStatusCode());
    }
}
