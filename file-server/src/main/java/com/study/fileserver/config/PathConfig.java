package com.study.fileserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PathConfig {
    @Bean
    public Path path(){
        return Paths.get("/uploads").toAbsolutePath().normalize();
    }
}
