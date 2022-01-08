package com.study.springsecurity.redis;

import com.study.springsecurity.redis.RedisDao;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisDao, String> {
    boolean existsById(String id);
}
