package com.study.springsecurity.model.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
