package com.poc.springbootredis.gateway.http;

import com.poc.springbootredis.domain.User;
import com.poc.springbootredis.domain.UserWrapper;
import com.poc.springbootredis.gateway.http.converter.UserToUserWrapper;
import com.poc.springbootredis.usecase.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private static final String RECEIVED_FROM_POST_DATA = "Received from POST data {}";
    private final UserLogin userLogin;
    private final UserToUserWrapper userToUserWrapper;

    @GetMapping("flavour1")
    public ResponseEntity<User> login(User user) {
        log.info("Received data {}", user);

        return ResponseEntity.ok(userLogin.getUser(user));
    }

    @PostMapping("flavour2")
    public ResponseEntity<User> getFromSpringRepositoryInterfaces(@RequestBody final User user) {
        log.info(RECEIVED_FROM_POST_DATA, user);
        return ResponseEntity.ok(userLogin.getUserUsingSpringRepositoryInterface(user));
    }

    @PostMapping("flavour3")
    public ResponseEntity<User> getFromCustomRedisTemplate(@RequestBody final User user) {
        log.info(RECEIVED_FROM_POST_DATA, user);

        return ResponseEntity.ok(userLogin.getUserUsingRedisTemplate(user));
    }

    @PostMapping("flavour4")
    public ResponseEntity<User> getFromUserWrapper(@RequestBody final User user) {
        log.info(RECEIVED_FROM_POST_DATA, user);
        final UserWrapper convert = userToUserWrapper.convert(user);

        return ResponseEntity.ok(userLogin.getUseWithinUserWrapper(convert));
    }
}
