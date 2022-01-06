package com.study.redis.repository;

import com.study.redis.model.RedisDao;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisDao, String> {
}
