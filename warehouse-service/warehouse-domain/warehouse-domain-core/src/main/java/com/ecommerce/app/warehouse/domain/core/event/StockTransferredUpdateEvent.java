package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;

import java.time.ZonedDateTime;

public class StockTransferredUpdateEvent extends StockEvent{
    private final Stock stockReceive;
    private final DomainEventPublisher<StockTransferredUpdateEvent> stockUpdatedEventPublisher;

    public StockTransferredUpdateEvent(Stock stock, StockJournal stockJournal, ZonedDateTime createdAt, Stock stockReceive, DomainEventPublisher<StockTransferredUpdateEvent> stockUpdatedEventPublisher) {
        super(stock, stockJournal, createdAt);
        this.stockReceive = stockReceive;
        this.stockUpdatedEventPublisher = stockUpdatedEventPublisher;
    }

    public Stock getStockReceived() {
        return stockReceive;
    }

    @Override
    public void fire() {
        stockUpdatedEventPublisher.publish(this);
    }
}
