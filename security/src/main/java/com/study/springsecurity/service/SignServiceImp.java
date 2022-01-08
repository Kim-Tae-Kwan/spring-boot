package com.study.springsecurity.service;

import com.study.springsecurity.execption.LoginException;
import com.study.springsecurity.execption.LogoutException;
import com.study.springsecurity.execption.ReissueException;
import com.study.springsecurity.model.dto.*;
import com.study.springsecurity.model.entity.Member;
import com.study.springsecurity.model.entity.Role;
import com.study.springsecurity.redis.RedisDao;
import com.study.springsecurity.redis.RedisRepository;
import com.study.springsecurity.repository.MemberRepository;
import com.study.springsecurity.repository.RoleRepository;
import com.study.springsecurity.utils.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Getter
@Service
public class SignServiceImp implements SignService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final RedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        Member member = memberRepository.findByEmail(request.getEmail());
        if (member == null){
            throw new LoginException("일치하는 아이디가 없습니다.");
        }

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new LoginException("비밀번호가 일치하지 않습니다.");
        }

        //토큰 생성
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), member.getRoles());
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail());

        redisRepository.save(RedisDao.builder()
                .id("RT " + member.getEmail())
                .token(refreshToken)
                .timeout(jwtProvider.getRefreshTokenValidMilliSecond())
                .build());

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(LogoutRequestDto request) {
        String accessToken = request.getAccessToken();
        if(!jwtProvider.validateToken(accessToken)){
            throw new LogoutException("access 토큰이 유효하지 않습니다.");
        }

        String email = jwtProvider.getAuthentication(accessToken).getName();
        redisRepository.deleteById("RT " + email);

        Long expiration = jwtProvider.getExpiration(accessToken);
        redisRepository.save(RedisDao.builder()
                .id("logout " + email)
                .token(accessToken)
                .timeout(expiration)
                .build());
    }

    @Override
    public SignupResponseDto singUp(SignupRequestDto request) {
        Member member = Member.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Role role = roleRepository.findByName("USER");
        member.getRoles().add(role);

        memberRepository.save(member);
        return SignupResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .roles(member.getRoles())
                .build();
    }

    @Override
    public ReissueDto reissue(ReissueDto reissueDto){
        String refreshToken = reissueDto.getRefreshToken();
        if(!jwtProvider.validateToken(refreshToken)){
            log.error("refresh token is invalid");
            throw  new ReissueException("refresh 토큰이 유효하지 않습니다.");
        }

        String email = jwtProvider.getAuthentication(refreshToken).getName();

        RedisDao redisDao = redisRepository.findById("RT " + email).get();
        if(!refreshToken.equals(redisDao.getToken())){
            log.error("refresh token is different wih stored token");
            throw  new ReissueException("refresh 토큰이 유효하지 않습니다.");
        }

        Member member = memberRepository.findByEmail(email);
        String newAccessToken = jwtProvider.createAccessToken(member.getEmail(), member.getRoles());
        String newRefreshToken = jwtProvider.createRefreshToken(member.getEmail());

        redisRepository.save(RedisDao.builder()
                .id("RT " + member.getEmail())
                .token(newRefreshToken)
                .timeout(jwtProvider.getRefreshTokenValidMilliSecond())
                .build());

        return ReissueDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
