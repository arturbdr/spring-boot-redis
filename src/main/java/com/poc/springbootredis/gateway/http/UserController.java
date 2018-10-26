package com.poc.springbootredis.gateway.http;

import com.poc.springbootredis.domain.User;
import com.poc.springbootredis.usecase.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserLogin userLogin;

    @GetMapping("test-cache")
    public ResponseEntity<User> login(User user) {
        log.info("Received data {}", user);

        return ResponseEntity.ok(userLogin.getUser(user));
    }
}
