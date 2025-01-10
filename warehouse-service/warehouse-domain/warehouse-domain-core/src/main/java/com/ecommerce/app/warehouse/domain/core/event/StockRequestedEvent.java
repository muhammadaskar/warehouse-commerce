package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;

import java.time.ZonedDateTime;

public class StockRequestedEvent extends StockEvent{
    private final DomainEventPublisher<StockRequestedEvent> stockRequestedEventPublisher;

    public StockRequestedEvent(Stock stock, StockJournal stockJournal, ZonedDateTime createdAt, DomainEventPublisher<StockRequestedEvent> stockRequestedEventPublisher) {
        super(stock, stockJournal, createdAt);
        this.stockRequestedEventPublisher = stockRequestedEventPublisher;
    }

    @Override
    public void fire() {
        stockRequestedEventPublisher.publish(this);
    }
}
