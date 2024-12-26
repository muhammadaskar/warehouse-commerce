package com.ecommerce.app.user.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.event.UserCreatedEvent;
import com.ecommerce.app.user.domain.core.event.WarehouseAdminCreatedEvent;

import java.time.ZonedDateTime;

public class UserDomainServiceImpl implements UserDomainService {

    @Override
    public UserCreatedEvent userCreated(User user, DomainEventPublisher<UserCreatedEvent> userCreatedEventDomainEventPublisher) {
        user.initializeCustomer();
        user.validateCustomer();
        return new UserCreatedEvent(user, ZonedDateTime.now(), userCreatedEventDomainEventPublisher);
    }

    @Override
    public void login(User user) {
        user.validateLogin();
    }

    @Override
    public void verifyEmail(User user) {
        user.verifyEmail();
    }

    @Override
    public void createPassword(User user, String password, String confirmPassword) {
        user.validatePassword(password, confirmPassword);
    }

    @Override
    public WarehouseAdminCreatedEvent warehouseAdminCreated(User user, DomainEventPublisher<WarehouseAdminCreatedEvent> warehouseAdminCreatedEventDomainEventPublisher) {
        user.validateAdmin();
        user.initializeAdmin();
        return new WarehouseAdminCreatedEvent(user, ZonedDateTime.now(), warehouseAdminCreatedEventDomainEventPublisher);
    }
}
