package com.learn.springbootredis.gateway.http;

import com.learn.springbootredis.usecase.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IPController {

  private final UserLogin userLogin;

  @PostMapping("/{ip}")
  public ResponseEntity<Long> login(@PathVariable String ip, HttpServletRequest request) {
    long login = userLogin.login(ip);
    log.info("Custom login count {}", login);

    return ResponseEntity.ok(userLogin.login(request.getRemoteAddr()));
  }
}
