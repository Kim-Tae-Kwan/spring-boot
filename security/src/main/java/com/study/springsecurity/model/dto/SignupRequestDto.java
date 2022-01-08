package com.study.springsecurity.model.dto;

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