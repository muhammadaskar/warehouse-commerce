package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;

import java.time.ZonedDateTime;

public class StockUpdatedEvent extends StockEvent{
    private final DomainEventPublisher<StockUpdatedEvent> stockUpdatedEventPublisher;

    public StockUpdatedEvent(Stock stock, ZonedDateTime createdAt, DomainEventPublisher<StockUpdatedEvent> stockUpdatedEventPublisher) {
        super(stock, createdAt);
        this.stockUpdatedEventPublisher = stockUpdatedEventPublisher;
    }

    @Override
    public void fire() {
        stockUpdatedEventPublisher.publish(this);
    }
}
