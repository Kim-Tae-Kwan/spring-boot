package com.study.springsecurity.service;

import com.study.springsecurity.model.Member;
import com.study.springsecurity.model.Role;
import com.study.springsecurity.repository.MemberRepository;
import com.study.springsecurity.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Transactional
@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Override
    public Member saveMember(Member member) {
        log.info("Saving new {} to the database", member.getName());
        return memberRepository.save(member);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToMember(String email, String roleName) {
        log.info("Adding role {} to email {}", roleName, email);
        Member member = memberRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        member.getRoles().add(role);
    }

    @Override
    public Member getMember(String email) {
        log.info("Fetching member {}", email);
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> getMembers() {
        log.info("Fetching members");
        return memberRepository.findAll();
    }
}
