package com.study.redis;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RedisService {
    private final RedisRepository repository;

    public RedisDao save(RedisDto redisDto){
        RedisDao redisDao = RedisDao.builder()
                .token(redisDto.getToken())
                .build();
        return repository.save(redisDao);
    }
}
