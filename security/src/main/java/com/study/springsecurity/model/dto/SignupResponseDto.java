package com.study.springsecurity.model.dto;

import com.study.springsecurity.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SignupResponseDto {
    private String id;
    private String name;
    private String email;
    private List<Role> roles;
}
