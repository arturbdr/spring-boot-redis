package com.poc.springbootredis.usecase;

import com.poc.springbootredis.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserLogin {

    @Cacheable(value = "users", key = "#user.hashCode()")
    public User getUser(User user) {
        log.info("Inside the usecase with the following user {}", user);
        return user;
    }
}
