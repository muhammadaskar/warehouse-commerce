package com.ecommerce.app.user.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.event.UserCreatedEvent;
import com.ecommerce.app.user.domain.core.event.WarehouseAdminCreatedEvent;

public interface UserDomainService {
    UserCreatedEvent userCreated(User user, DomainEventPublisher<UserCreatedEvent> userCreatedEventDomainEventPublisher);
    void login(User user);
    void verifyEmail(User user);
    void createPassword(User user, String password, String confirmPassword);
    WarehouseAdminCreatedEvent warehouseAdminCreated(User user, DomainEventPublisher<WarehouseAdminCreatedEvent> warehouseAdminCreatedEventDomainEventPublisher);

}
