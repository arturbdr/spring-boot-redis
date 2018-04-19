package com.learn.springbootredis.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

@Configuration
public class RedisConfig {

  @Bean
  public RedisConnectionFactory getRedisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName("localhost");
    jedisConnectionFactory.setPort(6379);
    return jedisConnectionFactory;
  }

  @Bean
  public RedisTemplate getRedisTemplate() {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(getRedisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
    return redisTemplate;
  }

//  @Bean
//    public RedisAtomicLong getRedisAtomicLong() {
//      Re
//  }
}