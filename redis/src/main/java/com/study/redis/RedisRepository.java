package com.study.redis;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisDao, String> {
}
