package com.poc.springbootredis.gateway.http;

import com.poc.springbootredis.domain.User;
import com.poc.springbootredis.gateway.http.converter.UserToUserWrapper;
import com.poc.springbootredis.usecase.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private static final String RECEIVED_FROM_POST_DATA = "Received from POST data {}";
    private final UserLogin userLogin;
    private final UserToUserWrapper userToUserWrapper;

    @GetMapping("flavour1")
    public ResponseEntity<User> flavour1(User user) {
        log.info("Received data {}", user);

        return ResponseEntity.ok(userLogin.flavour1(user));
    }

    @PostMapping("flavour2")
    public ResponseEntity<User> flavour2(@RequestBody final User user) {
        log.info(RECEIVED_FROM_POST_DATA, user);
        return ResponseEntity.ok(userLogin.flavour2(user));
    }

    @PostMapping("flavour3")
    public ResponseEntity<User> flavour3(@RequestBody final User user) {
        log.info(RECEIVED_FROM_POST_DATA, user);

        return ResponseEntity.ok(userLogin.flavour3(user));
    }

    @PostMapping("ttl1")
    public ResponseEntity<User> ttl1(@RequestBody final User user) throws IOException {
        log.info(RECEIVED_FROM_POST_DATA, user);

        return ResponseEntity.ok(userLogin.ttl1(user));
    }
}
