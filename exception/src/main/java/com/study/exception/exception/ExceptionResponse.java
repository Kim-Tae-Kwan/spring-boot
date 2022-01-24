package com.study.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

@Getter
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private String message;  // Message.
    private int status;   // Http status code.
    private String error; // Http status code message.
    private String path;  // URL path.

    public static ExceptionResponse of(ExceptionCode exceptionCode, HttpServletRequest request){
        return ExceptionResponse.builder()
                .message(exceptionCode.getMessage())
                .status(exceptionCode.getStatusCode().value())
                .error(exceptionCode.getStatusCode().name())
                .path(request.getServletPath())
                .build();
    }

}
