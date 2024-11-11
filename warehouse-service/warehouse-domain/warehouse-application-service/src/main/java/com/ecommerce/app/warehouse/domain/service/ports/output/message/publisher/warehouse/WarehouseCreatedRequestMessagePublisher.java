package com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.warehouse;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;

public interface WarehouseCreatedRequestMessagePublisher extends DomainEventPublisher<WarehouseCreatedEvent> {
}
