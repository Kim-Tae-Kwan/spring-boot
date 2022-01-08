package com.study.springsecurity.controller;

import com.study.springsecurity.model.entity.Member;
import com.study.springsecurity.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"2. Member"})
@AllArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "전체 멤버 조회", notes = "전체 멤버 조회를 한다.")
    @GetMapping
    public ResponseEntity<List<Member>> getMembers(@ApiParam(value = "User 토큰", required = true) @RequestHeader(value = "Authorization") String token){
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

}
