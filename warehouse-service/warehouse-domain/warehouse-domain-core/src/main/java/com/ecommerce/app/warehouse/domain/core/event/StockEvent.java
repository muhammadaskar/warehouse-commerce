package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;

import java.time.ZonedDateTime;

public abstract class StockEvent implements DomainEvent {

    private final Stock stock;
    private final ZonedDateTime createdAt;

    protected StockEvent(Stock stock, ZonedDateTime createdAt) {
        this.stock = stock;
        this.createdAt = createdAt;
    }

    public Stock getStock() {
        return stock;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
