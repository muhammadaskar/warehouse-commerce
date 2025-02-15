package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;

import java.time.ZonedDateTime;

public class StockTransferredEvent extends StockEvent{
    private final DomainEventPublisher<StockTransferredEvent> stockTransferredEventPublisher;

    public StockTransferredEvent(Stock stock, StockJournal stockJournal, ZonedDateTime createdAt, DomainEventPublisher<StockTransferredEvent> stockTransferredEventPublisher) {
        super(stock, stockJournal, createdAt);
        this.stockTransferredEventPublisher = stockTransferredEventPublisher;
    }

    @Override
    public void fire() {
        stockTransferredEventPublisher.publish(this);
    }
}
