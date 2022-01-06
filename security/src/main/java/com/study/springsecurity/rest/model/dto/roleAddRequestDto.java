package com.study.springsecurity.rest.model.dto;

import lombok.Data;

@Data
public class roleAddRequestDto{
    private String email;
    private String roleName;
}
