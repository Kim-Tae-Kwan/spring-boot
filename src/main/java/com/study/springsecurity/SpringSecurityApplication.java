package com.study.springsecurity;

import com.study.springsecurity.model.entity.Member;
import com.study.springsecurity.model.entity.Role;
import com.study.springsecurity.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner run(MemberService memberService){
//        return args -> {
//            memberService.saveRole(new Role(null, "USER"));
//            memberService.saveRole(new Role(null, "ADMIN"));
//            memberService.saveRole(new Role(null, "MASTER"));
//
//            memberService.saveMember(new Member(null, "kim", "tkk@vazilcompany.com", "1234", new ArrayList<>()));
//            memberService.saveMember(new Member(null, "seo", "seo@vazilcompany.com", "1234", new ArrayList<>()));
//            memberService.saveMember(new Member(null, "ghp", "ghp@vazilcompany.com", "1234", new ArrayList<>()));
//
//            memberService.addRoleToMember("tkk@vazilcompany.com", "USER");
//            memberService.addRoleToMember("seo@vazilcompany.com", "ADMIN");
//            memberService.addRoleToMember("ghp@vazilcompany.com", "MASTER");
//
//        };
//    }
}
