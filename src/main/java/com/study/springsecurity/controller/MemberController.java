package com.study.springsecurity.controller;

import com.study.springsecurity.model.Member;
import com.study.springsecurity.model.Role;
import com.study.springsecurity.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping("/member")
    public ResponseEntity<Member> saveMember(@RequestBody Member member){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/member")
                .toUriString());

        return ResponseEntity.created(uri).body(memberService.saveMember(member));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/role")
                .toUriString());

        return ResponseEntity.created(uri).body(memberService.saveRole(role));
    }

    @PutMapping("/member/role")
    public ResponseEntity<Role> addRoleToMember(@RequestBody roleAddRequestDto requestDto){
        memberService.addRoleToMember(requestDto.getEmail(), requestDto.getRoleName());
        return ResponseEntity.ok().build();
    }
}

@Data
class roleAddRequestDto{
    private String email;
    private String roleName;
}
