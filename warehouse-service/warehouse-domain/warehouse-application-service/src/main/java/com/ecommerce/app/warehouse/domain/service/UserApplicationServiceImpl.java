package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.core.entity.User;
import com.ecommerce.app.warehouse.domain.service.ports.input.service.UserApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserHandler userHandler;

    public UserApplicationServiceImpl(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @Override
    public User checkActiveUser(String userId) {
        return userHandler.checkActiveUser(userId);
    }
}
