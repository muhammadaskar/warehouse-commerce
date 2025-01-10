package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.StockJournal;

import java.time.ZonedDateTime;

public abstract class StockEvent implements DomainEvent {

    private final Stock stock;
    private final StockJournal stockJournal;
    private final ZonedDateTime createdAt;

    public StockEvent(Stock stock, StockJournal stockJournal, ZonedDateTime createdAt) {
        this.stock = stock;
        this.stockJournal = stockJournal;
        this.createdAt = createdAt;
    }

    public Stock getStock() {
        return stock;
    }

    public StockJournal getStockJournal() {
        return stockJournal;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
