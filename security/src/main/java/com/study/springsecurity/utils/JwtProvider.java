package com.study.springsecurity.utils;

import com.study.springsecurity.model.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Component
public class JwtProvider {
    //    @Value("spring.jwt.secret")
    private String secretKey;
    private long accessTokenValidMilliSecond;
    private long refreshTokenValidMilliSecond;
    private final UserDetailsService userDetailsService;

    @PostConstruct //초기화 작업을 위해 실행하는 메소드
    protected void init() {
        String temp = "vazil.vtalk";
        secretKey = Base64.getEncoder().encodeToString(temp.getBytes());
        accessTokenValidMilliSecond = 10 * 60 * 1000L; // 10분 토큰 유효
        refreshTokenValidMilliSecond = 30 * 60 * 1000L; // 30분 토큰 유효
    }

    // Jwt 토큰 생성
    public String createAccessToken(String userName, List<Role> roles) {
        return this.createToken(userName, roles);
    }

    // Jwt 토큰 생성
    public String createRefreshToken(String userName) {
        return this.createToken(userName, null);
    }

    // Jwt 토큰 생성
    private String createToken(String userName, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(userName);
        long expireTime = this.refreshTokenValidMilliSecond;
        if (roles != null){
            claims.put("roles", roles.stream().map(Role::getName).collect(Collectors.toList()));
            expireTime = this.accessTokenValidMilliSecond;
        }
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + expireTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    private String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱 : "Authorization: jwt토큰"
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null && token.length() > 7){
            return token.substring(7);
        }else {
            return "";
        }
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken); //Jwt 토큰의 유효성
            return !claims.getBody().getExpiration().before(new Date()); //만료일자 확인
        } catch (Exception e) {
            return false;
        }
    }

    //token 현재 남은 만료 시간(ms)
    public Long getExpiration(String token){
        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}
