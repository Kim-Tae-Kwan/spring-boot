package com.study.springsecurity.service;

import com.study.springsecurity.model.entity.Member;
import com.study.springsecurity.model.entity.Role;
import com.study.springsecurity.repository.MemberRepository;
import com.study.springsecurity.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@Transactional
@AllArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Member saveMember(Member member) {
        log.info("Saving new {} to the database", member.getName());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    public Role saveRole(Role role) {
        log.info("Saving new {} to the database", role.getName());
        return roleRepository.save(role);
    }

    public void addRoleToMember(String email, String roleName) {
        log.info("Adding role {} to email {}", roleName, email);
        Member member = memberRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        member.getRoles().add(role);
    }

    public Member findByEmail(String email) {
        log.info("Fetching member {}", email);
        return memberRepository.findByEmail(email);
    }

    public List<Member> getMembers() {
        log.info("Fetching members");
        return memberRepository.findAll();
    }
}
