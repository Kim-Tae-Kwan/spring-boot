package com.study.springsecurity.security.model;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
