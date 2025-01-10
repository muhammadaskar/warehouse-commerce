package com.ecommerce.app.order.application.service;

import com.ecommerce.app.order.domain.core.entity.User;
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
        log.info("Checking if user is active with id: {}", userId);
        return userHelper.checkActiveUser(userId);
    }
}
