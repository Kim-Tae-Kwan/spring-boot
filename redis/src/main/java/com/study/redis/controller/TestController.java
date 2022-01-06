package com.study.redis.controller;

import com.study.redis.model.RedisDao;
import com.study.redis.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/redis")
@RestController
public class TestController {

    private final RedisService redisService;

    @GetMapping("/repo/{key}")
    public RedisDao getValueWithRepo(@PathVariable String key){
        return redisService.getValue(key);
    }

    @GetMapping("/temp/{key}")
    public Object getValueWithTemp(@PathVariable String key){
        return redisService.getValueWithTemplate(key);
    }

    @PostMapping("/repo")
    public void save(@RequestBody RedisDao redisDao){
        redisService.save(redisDao);
    }

    @PostMapping("/temp")
    public void saveWithTemp(@RequestBody RedisDao redisDao){
        redisService.saveWithTemplate(redisDao);
    }
}
