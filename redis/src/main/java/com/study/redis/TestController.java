package com.study.redis;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/redis")
@RestController
public class TestController {

    private final RedisService redisService;

    @PostMapping
    public void save(@RequestBody RedisDto redisDto){
        redisService.save(redisDto);
    }
}
