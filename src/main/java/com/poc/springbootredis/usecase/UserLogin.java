package com.poc.springbootredis.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.springbootredis.domain.User;
import com.poc.springbootredis.domain.UserWrapper;
import com.poc.springbootredis.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserLogin {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    // First Flavour - Spring magic annotation
    @Cacheable(value = "users", key = "#user.hashCode()")
    public User getUser(User user) {
        log.info("Inside the usecase with the following user {}", user);

        return user;
    }

    // Partial Control with Spring Spring Repository interface
    public User getUserUsingSpringRepositoryInterface(final User user) {
        final Optional<User> optionalUser = userRepository
                .findById(user.getName())
                .or(() -> {
                    log.info("Inside the usecase with the following user {}", user);
                    return Optional.of(userRepository.save(user));
                });

        return optionalUser.get();
    }


    // Third flavor (full verbose but full control)
    public User getUserUsingRedisTemplate(final User user) {
        final String userCached = redisTemplate.opsForValue().get(user.getName());

        if (userCached != null) {
            log.info("Returning from CACHE {}", user);
            try {
                return objectMapper.readValue(userCached, User.class);
            } catch (IOException e) {
                ReflectionUtils.rethrowRuntimeException(e);
            }
        } else {
            log.info("Storing on CACHE {}", user);
            try {
                // LOGIC - DONT FORGET TO PUT THE EXPIRATION OF THE KEY
                redisTemplate.opsForValue().set(user.getName(), objectMapper.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                ReflectionUtils.rethrowRuntimeException(e);
            }
        }
        return user;
    }

    public User getUseWithinUserWrapper(final UserWrapper userWrapper) {

        final String userCached = redisTemplate.opsForValue().get(userWrapper.getCacheKey());

        if (userCached != null) {
            log.info("Returning from CACHE {}", userWrapper.getCacheKey());
            try {
                return objectMapper.readValue(userCached, User.class);
            } catch (IOException e) {
                ReflectionUtils.rethrowRuntimeException(e);
            }
        } else {
            log.info("Storing on CACHE {}", userWrapper.getCacheKey());
            try {
                // LOGIC - DONT FORGET TO PUT THE EXPIRATION OF THE KEY
                redisTemplate.opsForValue().set(userWrapper.getCacheKey(), objectMapper.writeValueAsString(userWrapper.getUser()));
            } catch (JsonProcessingException e) {
                ReflectionUtils.rethrowRuntimeException(e);
            }
        }
        return userWrapper.getUser();
    }
}
