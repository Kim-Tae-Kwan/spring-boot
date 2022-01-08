package com.study.springsecurity.execption;

public class LoginException extends RuntimeException{

    public LoginException(String message) {
        super(message);
    }
}
