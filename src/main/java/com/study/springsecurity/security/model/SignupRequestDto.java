package com.study.springsecurity.security.model;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class SignupRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
}