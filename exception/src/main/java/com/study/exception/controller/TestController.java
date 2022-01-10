package com.study.exception.controller;

import com.study.exception.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/test")
@RestController
public class TestController {
    private final TestService testService;

    @GetMapping
    public Object get(){
        return testService.test();
    }
}
