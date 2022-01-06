package com.study.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@Builder
@RedisHash("redis")
public class RedisDao {

    @Id
    private String id;
    private String token;
}
