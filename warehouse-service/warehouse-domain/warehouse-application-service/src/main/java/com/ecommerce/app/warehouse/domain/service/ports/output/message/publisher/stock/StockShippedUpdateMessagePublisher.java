package com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.event.StockShippedUpdateEvent;

public interface StockShippedUpdateMessagePublisher extends DomainEventPublisher<StockShippedUpdateEvent> {
}
