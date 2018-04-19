package com.learn.springbootredis.gateway.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UserRedis {

    public static final int INITIAL_VALUE = 1;
    public static final int INCREMENT_VALUE = 1;
    private final RedisTemplate<String, Integer> redisTemplate;

    public long incrementAndGet(String key) {
        Integer counter = redisTemplate.boundValueOps(key).get();
        if (counter == null || counter.equals(0)) {
            redisTemplate.boundValueOps(key).set(INITIAL_VALUE, 30, TimeUnit.SECONDS);
            return INITIAL_VALUE;
        }

        return redisTemplate.boundValueOps(key).increment(INCREMENT_VALUE);
    }

}
