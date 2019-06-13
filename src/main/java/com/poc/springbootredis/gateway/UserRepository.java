package com.poc.springbootredis.gateway;

import com.poc.springbootredis.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
