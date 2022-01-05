package com.study.springsecurity.rest.service;

import com.study.springsecurity.rest.model.entity.Member;
import com.study.springsecurity.rest.model.entity.Role;
import com.study.springsecurity.rest.repository.MemberRepository;
import com.study.springsecurity.rest.repository.RoleRepository;
import com.study.springsecurity.security.model.LoginRequestDto;
import com.study.springsecurity.security.model.LoginResponseDto;
import com.study.springsecurity.security.model.SignupRequestDto;
import com.study.springsecurity.security.utils.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Transactional
@AllArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public List<Member> findAll() {
        log.info("Fetching members");
        return memberRepository.findAll();
    }

    public Member findByEmail(String email) {
        log.info("Fetching member {}", email);
        return memberRepository.findByEmail(email);
    }

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

    public LoginResponseDto login(LoginRequestDto request) throws NoSuchElementException{
        Member member = memberRepository.findByEmail(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new NoSuchElementException();
        }

        //토큰 생성
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), member.getRoles());
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail());

        return LoginResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
    }

    public Member singUp(SignupRequestDto request){
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Role role = roleRepository.findByName("USER");
        member.getRoles().add(role);

        return memberRepository.save(member);
    }
}
