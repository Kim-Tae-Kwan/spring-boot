package com.study.springsecurity.service;

import com.study.springsecurity.model.Member;
import com.study.springsecurity.model.Role;

import java.util.List;

public interface MemberService {
    Member saveMember(Member member);
    Role saveRole(Role role) ;
    void addRoleToMember(String email, String roleName);
    Member getMember(String email);
    List<Member> getMembers();
}
