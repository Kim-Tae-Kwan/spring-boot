package com.study.springsecurity.controller;

import com.study.springsecurity.model.dto.*;
import com.study.springsecurity.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Sign"})
@AllArgsConstructor
@RestController
public class SignController {
    private final SignService signService;

    @ApiOperation(value = "로그인", notes = "로그인 한다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@ApiParam(value = "email, password", required = true) @RequestBody LoginRequestDto request){
        LoginResponseDto response = signService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃 한다.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@ApiParam(value = "access 토큰", required = true) @RequestBody LogoutRequestDto request){
        signService.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@ApiParam(value = "회원 정보", required = true) @RequestBody SignupRequestDto request){
        SignupResponseDto response = signService.singUp(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "토크 재발급", notes = "토크 재발급한다.")
    @PostMapping("/reissue")
    public ResponseEntity<ReissueDto> reissue(@ApiParam(value = "현재 토큰 정보", required = true) @RequestBody ReissueDto request){
        ReissueDto reissueDto = signService.reissue(request);
        return new ResponseEntity<>(reissueDto, HttpStatus.OK);
    }
}
