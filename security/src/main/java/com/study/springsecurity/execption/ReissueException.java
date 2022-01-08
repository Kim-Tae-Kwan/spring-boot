package com.study.springsecurity.execption;

public class ReissueException extends RuntimeException{
    public ReissueException(String message){
        super(message);
    }
}
