package com.learn.springbootredis.usecase;

import com.learn.springbootredis.gateway.redis.UserRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLogin {

  private final UserRedis userRedis;

  public long login(String ipAddress) {
    return userRedis.incrementAndGet(ipAddress);
  }
}
