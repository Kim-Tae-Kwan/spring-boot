package com.study.springsecurity;

import com.study.springsecurity.model.entity.Member;
import com.study.springsecurity.model.entity.Role;
import com.study.springsecurity.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(MemberService memberService){
        return args -> {
            List<Role> roles = memberService.findAllRole();
            if (roles.size() == 0){
                memberService.saveRole(new Role(null, "USER"));
                memberService.saveRole(new Role(null, "ADMIN"));
            }
        };
    }
}
