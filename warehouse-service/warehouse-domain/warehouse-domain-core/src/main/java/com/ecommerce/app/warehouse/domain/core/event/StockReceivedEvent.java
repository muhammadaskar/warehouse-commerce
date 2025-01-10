package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;

import java.time.ZonedDateTime;

public class StockReceivedEvent extends StockEvent{
    private final DomainEventPublisher<StockReceivedEvent> stockReceivedEventDomainEventPublisher;

    public StockReceivedEvent(Stock stock, StockJournal stockJournal, ZonedDateTime createdAt, DomainEventPublisher<StockReceivedEvent> stockReceivedEventDomainEventPublisher) {
        super(stock, stockJournal, createdAt);
        this.stockReceivedEventDomainEventPublisher = stockReceivedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        stockReceivedEventDomainEventPublisher.publish(this);
    }
}
