package com.ecommerce.app.warehouse.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.*;
import com.ecommerce.app.warehouse.domain.core.event.*;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class WarehouseDomainServiceImpl implements WarehouseDomainService {

    @Override
    public WarehouseCreatedEvent validateAndInitiateWarehouse(Warehouse warehouse, DomainEventPublisher<WarehouseCreatedEvent> warehouseCreatedEventDomainEventPublisher) {
        warehouse.validateWarehouse();
        warehouse.initializeWarehouse();
        return new WarehouseCreatedEvent(warehouse, ZonedDateTime.now(), warehouseCreatedEventDomainEventPublisher);
    }

    @Override
    public OrderWarehouseResponseEvent responseOrder(Order order, List<Warehouse> warehouses, DomainEventPublisher<OrderWarehouseResponseEvent> orderWarehouseResponseEventDomainEventPublisher) {
        order.validateStockInWarehouses(warehouses);
        return new OrderWarehouseResponseEvent(order, ZonedDateTime.now(), orderWarehouseResponseEventDomainEventPublisher);
    }

    @Override
    public StockTransferredUpdateEvent stockUpdatedAfterAutomaticallyTransfer(Product product, Stock stockSource, Stock stockDestination, int quantity, DomainEventPublisher<StockTransferredUpdateEvent> stockUpdatedEventDomainEventPublisher) {
        stockSource.setProduct(product);
        stockSource.decrease(quantity);
        stockDestination.setProduct(product);
        stockDestination.increase(quantity);
        return new StockTransferredUpdateEvent(stockSource, null, ZonedDateTime.now(), stockDestination, stockUpdatedEventDomainEventPublisher);
    }

    @Override
    public OrderShippedResponseEvent responseOrderShipped(Order order, Warehouse currenWarehouse, DomainEventPublisher<OrderShippedResponseEvent> orderShippedResponseEventDomainEventPublisher) {
        order.validateStockBeforeShipped(currenWarehouse);
        return new OrderShippedResponseEvent(order, ZonedDateTime.now(), orderShippedResponseEventDomainEventPublisher);
    }

    @Override
    public StockShippedUpdateEvent updateStockShipped(List<Stock> stocks,  DomainEventPublisher<StockShippedUpdateEvent> stockShippedUpdateEventDomainEventPublisher) {
        return new StockShippedUpdateEvent(stocks, ZonedDateTime.now(), stockShippedUpdateEventDomainEventPublisher);
    }

    @Override
    public StockUpdatedEvent stockUpdated(Product product, Stock stock, StockJournal stockJournal, int quantity, DomainEventPublisher<StockUpdatedEvent> stockUpdatedEventDomainEventPublisher) {
        stock.updateStock(product, quantity, stockJournal);
        return new StockUpdatedEvent(stock, stockJournal, ZonedDateTime.now(), stockUpdatedEventDomainEventPublisher);
    }

    @Override
    public StockCreatedEvent stockCreated(Warehouse warehouse, Product product, Stock stock, StockJournal stockJournal, int quantity, DomainEventPublisher<StockCreatedEvent> stockCreatedEventDomainEventPublisher) {
        stock.createStock(warehouse, product, quantity, stockJournal);
        return new StockCreatedEvent(stock, stockJournal, ZonedDateTime.now(), stockCreatedEventDomainEventPublisher);
    }
}
