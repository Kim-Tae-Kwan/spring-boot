package com.study.springsecurity.controller;

import com.study.springsecurity.model.Member;
import com.study.springsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMembers(){
        return ResponseEntity.ok().body(memberService.getMembers());
    }
}
