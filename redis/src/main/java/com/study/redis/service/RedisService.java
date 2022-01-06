package com.study.redis.service;

import com.study.redis.model.RedisDao;
import com.study.redis.repository.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RedisService {
    private final RedisRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisDao getValue(String key){
        return repository.findById(key).get();
    }

    public void save(RedisDao redisDao){
        redisDao.setExpireTime(10L);
        repository.save(redisDao);
    }

    public Object getValueWithTemplate(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void saveWithTemplate(RedisDao redisDao){
        redisTemplate.opsForValue().set(redisDao.getId(), redisDao.getToken());
    }
}
