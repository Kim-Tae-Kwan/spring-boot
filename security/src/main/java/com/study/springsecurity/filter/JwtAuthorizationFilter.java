package com.study.springsecurity.filter;

import com.study.springsecurity.redis.RedisDao;
import com.study.springsecurity.redis.RedisRepository;
import com.study.springsecurity.utils.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;
    private final RedisRepository redisRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        if(token != null && jwtProvider.validateToken(token)){
            String email = jwtProvider.getAuthentication(token).getName();
            if(!redisRepository.existsById("logout " + email)){
                Authentication auth = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request,response);
    }
}
