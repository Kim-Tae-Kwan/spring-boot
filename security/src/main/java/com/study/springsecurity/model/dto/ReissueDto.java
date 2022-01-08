package com.study.springsecurity.model.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReissueDto {
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;
}
