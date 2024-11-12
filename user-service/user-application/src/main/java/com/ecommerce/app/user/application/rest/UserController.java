package com.ecommerce.app.user.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
public class UserController {

    @GetMapping
    public ResponseEntity<String> getList() {
        log.info("getList");
        return ResponseEntity.ok("Hello World");
    }
}
