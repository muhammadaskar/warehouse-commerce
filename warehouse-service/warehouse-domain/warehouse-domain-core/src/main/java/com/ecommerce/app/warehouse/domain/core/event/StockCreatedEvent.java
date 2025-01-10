package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;

import java.time.ZonedDateTime;

public class StockCreatedEvent extends StockEvent{

    private final DomainEventPublisher<StockCreatedEvent> stockCreatedEventPublisher;

    public StockCreatedEvent(Stock stock, StockJournal stockJournal, ZonedDateTime createdAt, DomainEventPublisher<StockCreatedEvent> stockCreatedEventPublisher) {
        super(stock, stockJournal, createdAt);
        this.stockCreatedEventPublisher = stockCreatedEventPublisher;
    }

    @Override
    public void fire() {
        stockCreatedEventPublisher.publish(this);
    }
}
