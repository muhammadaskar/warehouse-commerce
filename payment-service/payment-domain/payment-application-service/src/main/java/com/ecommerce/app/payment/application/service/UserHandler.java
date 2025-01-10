package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.payment.domain.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserHandler {

    private final UserHelper userHelper;

    public UserHandler(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    public User checkActiveUser(String userId) {
        log.info("Finding user by id: {}", userId);
        return userHelper.checkActiveUser(userId);
    }
}
