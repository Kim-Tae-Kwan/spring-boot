package com.study.springsecurity.security.controller;

import com.study.springsecurity.model.entity.Member;
import com.study.springsecurity.security.model.LoginRequestDto;
import com.study.springsecurity.security.model.LoginResponseDto;
import com.study.springsecurity.security.utils.JwtProvider;
import com.study.springsecurity.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
//@RequestMapping
@RestController
public class SignController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request){
        Member member = memberService.findByEmail(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        //토큰 생성
        String token = jwtProvider.createToken(member.getEmail(), member.getRoles());
        return new ResponseEntity<>(LoginResponseDto.builder().accessToken(token).refreshToken("").build(),HttpStatus.OK);
    }

}
