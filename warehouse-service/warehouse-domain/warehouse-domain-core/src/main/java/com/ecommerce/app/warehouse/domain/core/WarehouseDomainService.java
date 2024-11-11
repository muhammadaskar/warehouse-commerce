package com.ecommerce.app.warehouse.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.StockReceivedEvent;
import com.ecommerce.app.warehouse.domain.core.event.StockTransferredEvent;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;


public interface WarehouseDomainService {

    WarehouseCreatedEvent validateAndInitiateWarehouse(Warehouse warehouse, DomainEventPublisher<WarehouseCreatedEvent> warehouseCreatedEventDomainEventPublisher);

    StockTransferredEvent stockTransferredEvent(WarehouseId toWarehouseId, Stock stock, Product product, int quantity, DomainEventPublisher<StockTransferredEvent> stockTransferredEventDomainEventPublisher);
    StockReceivedEvent stockReceivedEvent(WarehouseId fromWarehouseId, Stock stock, Product product, int quantity, DomainEventPublisher<StockReceivedEvent> stockReceivedEventDomainEventPublisher);
}
