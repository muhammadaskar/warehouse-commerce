package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;

import java.time.ZonedDateTime;
import java.util.List;

public class StockShippedUpdateEvent implements DomainEvent {
    private final List<Stock> stocks;
    private final ZonedDateTime updatedAt;
    private final DomainEventPublisher<StockShippedUpdateEvent> domainEventPublisher;

    public StockShippedUpdateEvent(List<Stock> stocks, ZonedDateTime updatedAt, DomainEventPublisher<StockShippedUpdateEvent> domainEventPublisher) {
        this.stocks = stocks;
        this.updatedAt = updatedAt;
        this.domainEventPublisher = domainEventPublisher;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    @Override
    public void fire() {
        domainEventPublisher.publish(this);
    }
}
