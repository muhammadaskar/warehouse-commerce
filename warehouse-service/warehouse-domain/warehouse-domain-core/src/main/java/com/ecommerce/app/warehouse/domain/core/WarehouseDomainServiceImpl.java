package com.ecommerce.app.warehouse.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.StockReceivedEvent;
import com.ecommerce.app.warehouse.domain.core.event.StockTransferredEvent;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
public class WarehouseDomainServiceImpl implements WarehouseDomainService {

    @Override
    public WarehouseCreatedEvent validateAndInitiateWarehouse(Warehouse warehouse, DomainEventPublisher<WarehouseCreatedEvent> warehouseCreatedEventDomainEventPublisher) {
        warehouse.validateWarehouse();
        warehouse.initializeWarehouse();
        return new WarehouseCreatedEvent(warehouse, ZonedDateTime.now(), warehouseCreatedEventDomainEventPublisher);
    }

    // TODO: any adjustment
    @Override
    public StockTransferredEvent stockTransferredEvent(WarehouseId toWarehouseId,
                                                       Stock stock,
                                                       Product product,
                                                       int quantity,
                                                       DomainEventPublisher<StockTransferredEvent> stockTransferredEventDomainEventPublisher) {
        stock.transferStockTo(toWarehouseId, product, quantity);
        log.info("Stock transferred event received");
        return new StockTransferredEvent(stock, ZonedDateTime.now(), stockTransferredEventDomainEventPublisher);
    }

    // TODO: any adjustment
    @Override
    public StockReceivedEvent stockReceivedEvent(WarehouseId fromWarehouseId,
                                                 Stock stock,
                                                 Product product,
                                                 int quantity,
                                                 DomainEventPublisher<StockReceivedEvent> stockReceivedEventDomainEventPublisher) {
        stock.receiveStockFrom(fromWarehouseId, product, quantity);
        return new StockReceivedEvent(stock, ZonedDateTime.now(), stockReceivedEventDomainEventPublisher);
    }
}
