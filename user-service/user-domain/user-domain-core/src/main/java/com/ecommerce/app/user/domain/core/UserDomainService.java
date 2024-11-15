package com.ecommerce.app.user.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.event.WarehouseAdminCreatedEvent;

public interface UserDomainService {
    WarehouseAdminCreatedEvent warehouseAdminCreated(User user, DomainEventPublisher<WarehouseAdminCreatedEvent> warehouseAdminCreatedEventDomainEventPublisher);
}
