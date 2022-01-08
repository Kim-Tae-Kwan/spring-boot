package com.study.springsecurity.model.dto;

import lombok.Data;

@Data
public class LogoutRequestDto {
    private String accessToken;
}
