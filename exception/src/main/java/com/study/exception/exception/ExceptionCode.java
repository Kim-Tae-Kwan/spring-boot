package com.study.exception.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    //Exception 에러 코드 정의.
    TEST_EXCEPTION("Test", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus statusCode;

    ExceptionCode(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
