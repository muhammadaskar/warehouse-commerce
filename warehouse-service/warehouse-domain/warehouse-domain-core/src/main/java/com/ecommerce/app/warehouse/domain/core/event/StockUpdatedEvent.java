package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;

import java.time.ZonedDateTime;

public class StockUpdatedEvent extends StockEvent{

    private final DomainEventPublisher<StockUpdatedEvent> stockUpdatedEventPublisher;

    public StockUpdatedEvent(Stock stock, StockJournal stockJournal, ZonedDateTime createdAt, DomainEventPublisher<StockUpdatedEvent> stockUpdatedEventPublisher) {
        super(stock, stockJournal, createdAt);
        this.stockUpdatedEventPublisher = stockUpdatedEventPublisher;
    }

    @Override
    public void fire() {
        stockUpdatedEventPublisher.publish(this);
    }
}
