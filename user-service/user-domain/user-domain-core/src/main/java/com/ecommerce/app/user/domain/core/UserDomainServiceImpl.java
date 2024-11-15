package com.ecommerce.app.user.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.event.WarehouseAdminCreatedEvent;

import java.time.ZonedDateTime;

public class UserDomainServiceImpl implements UserDomainService {

    @Override
    public WarehouseAdminCreatedEvent warehouseAdminCreated(User user, DomainEventPublisher<WarehouseAdminCreatedEvent> warehouseAdminCreatedEventDomainEventPublisher) {
        user.validateAdmin();
        user.initializeAdmin();
        return new WarehouseAdminCreatedEvent(user, ZonedDateTime.now(), warehouseAdminCreatedEventDomainEventPublisher);
    }
}
