package com.ecommerce.app.warehouse.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.*;
import com.ecommerce.app.warehouse.domain.core.event.*;

import java.util.List;


public interface WarehouseDomainService {

    WarehouseCreatedEvent validateAndInitiateWarehouse(Warehouse warehouse,
                                                       DomainEventPublisher<WarehouseCreatedEvent> warehouseCreatedEventDomainEventPublisher);
    OrderWarehouseResponseEvent responseOrder(Order order,
                                              List<Warehouse> warehouses,
                                              DomainEventPublisher<OrderWarehouseResponseEvent> orderWarehouseResponseEventDomainEventPublisher);

    StockTransferredUpdateEvent stockUpdatedAfterAutomaticallyTransfer(Product product, Stock stockSource, Stock stockDestination, int quantity,
                                           DomainEventPublisher<StockTransferredUpdateEvent> stockUpdatedEventDomainEventPublisher);

    OrderShippedResponseEvent responseOrderShipped(Order order, Warehouse currentWarehouse,
                                                   DomainEventPublisher<OrderShippedResponseEvent> orderShippedResponseEventDomainEventPublisher);

    StockShippedUpdateEvent updateStockShipped(List<Stock> stocks,
                                              DomainEventPublisher<StockShippedUpdateEvent> stockShippedUpdateEventDomainEventPublisher);

    StockUpdatedEvent stockUpdated(Product product, Stock stock, StockJournal stockJournal, int quantity, DomainEventPublisher<StockUpdatedEvent> stockUpdatedEventDomainEventPublisher);

    StockCreatedEvent stockCreated(Warehouse warehouse, Product product, Stock stock, StockJournal stockJournal, int quantity, DomainEventPublisher<StockCreatedEvent> stockCreatedEventDomainEventPublisher);

}
