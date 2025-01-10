package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.warehouse.domain.core.WarehouseDomainService;
import com.ecommerce.app.warehouse.domain.core.entity.*;
import com.ecommerce.app.warehouse.domain.core.event.OrderShippedResponseEvent;
import com.ecommerce.app.warehouse.domain.core.event.OrderWarehouseResponseEvent;
import com.ecommerce.app.warehouse.domain.core.event.StockShippedUpdateEvent;
import com.ecommerce.app.warehouse.domain.core.event.StockTransferredUpdateEvent;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderPaidRequest;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderShippedRequest;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order.OrderShippedResponseMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order.OrderWarehouseResponseMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockShippedUpdateMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockTransferredUpdateMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderHelper {

    private final WarehouseDomainService warehouseDomainService;
    private final WarehouseDataMapper warehouseDataMapper;
    private final WarehouseHelper warehouseHelper;
    private final WarehouseStockHelper warehouseStockHelper;
    private final ProductHelper productHelper;
    private final OrderWarehouseResponseMessagePublisher orderWarehouseResponseMessagePublisher;
    private final StockTransferredUpdateMessagePublisher stockTransferredUpdateMessagePublisher;
    private final OrderShippedResponseMessagePublisher orderShippedResponseMessagePublisher;
    private final StockShippedUpdateMessagePublisher stockShippedUpdateMessagePublisher;

    public OrderHelper(WarehouseDomainService warehouseDomainService,
                       WarehouseDataMapper warehouseDataMapper,
                       WarehouseHelper warehouseHelper,
                       WarehouseStockHelper warehouseStockHelper, ProductHelper productHelper,
                       OrderWarehouseResponseMessagePublisher orderWarehouseResponseMessagePublisher,
                       StockTransferredUpdateMessagePublisher stockTransferredUpdateMessagePublisher,
                       OrderShippedResponseMessagePublisher orderShippedResponseMessagePublisher,
                       StockShippedUpdateMessagePublisher stockShippedUpdateMessagePublisher) {
        this.warehouseDomainService = warehouseDomainService;
        this.warehouseDataMapper = warehouseDataMapper;
        this.warehouseHelper = warehouseHelper;
        this.warehouseStockHelper = warehouseStockHelper;
        this.productHelper = productHelper;
        this.orderWarehouseResponseMessagePublisher = orderWarehouseResponseMessagePublisher;
        this.stockTransferredUpdateMessagePublisher = stockTransferredUpdateMessagePublisher;
        this.orderShippedResponseMessagePublisher = orderShippedResponseMessagePublisher;
        this.stockShippedUpdateMessagePublisher = stockShippedUpdateMessagePublisher;
    }

    /**
     * Process order paid event, after payment is approved by admin and order is automatically approved by system
     * @param orderPaidRequest order paid request
     * @return order warehouse response event
     */
    @Transactional
    public OrderWarehouseResponseEvent processOrderPaidEvent(OrderPaidRequest orderPaidRequest) {
        Order order = warehouseDataMapper.orderPaidRequestToOrder(orderPaidRequest);
        List<Warehouse> warehouses = warehouseHelper.findAllWarehouse();
        OrderWarehouseResponseEvent orderWarehouseResponseEvent = warehouseDomainService.
                responseOrder(order, warehouses, orderWarehouseResponseMessagePublisher);

        if (!order.getStockTransfers().isEmpty()) {
            updateTransferStocks(order);
        }

        log.info("Order with id: {} is processed", order.getId().getValue());
        return orderWarehouseResponseEvent;
    }

    /**
     * Process order shipped event, after warehouse admin ship the order
     * @param orderShippedRequest order shipped request
     */
    @Transactional
    public Map<OrderShippedResponseEvent, StockShippedUpdateEvent> processOrderShipped(OrderShippedRequest orderShippedRequest){
        Order order = warehouseDataMapper.orderShippedRequestToOrder(orderShippedRequest);
        Warehouse currentWarehouse = warehouseHelper.findWarehouse(order.getWarehouseId());

        OrderShippedResponseEvent orderShippedResponseEvent = null;
        StockShippedUpdateEvent stockShippedUpdateEvent = null;

        try {
            orderShippedResponseEvent = warehouseDomainService.
                    responseOrderShipped(order, currentWarehouse, orderShippedResponseMessagePublisher);
            List<Stock> stocks = new ArrayList<>();

            order.getOrderItems().forEach(orderItem -> {
                ProductId productId = orderItem.getProductId();
                int quantity = orderItem.getQuantity();
                Product product = productHelper.findProduct(productId);
                Stock stock = warehouseStockHelper.findStockByProductIdAndWarehouseId(productId, currentWarehouse.getId());
                stock.setProduct(product);
                stock.decrease(quantity);
                StockJournal journalStock = warehouseDataMapper.stockToStockJournal(stock);
                journalStock.reduceStock(quantity);
                warehouseStockHelper.saveStockJournal(journalStock);
                warehouseStockHelper.saveStock(stock);
                stocks.add(stock);
            });

            stockShippedUpdateEvent = warehouseDomainService.updateStockShipped(stocks , stockShippedUpdateMessagePublisher);
        } catch (Exception e) {
            log.error("Error processing order shipment for order id: {}", order.getId().getValue(), e);
        }

        return Map.of(orderShippedResponseEvent, stockShippedUpdateEvent);
    }

    /**
     * Update stock after transfer stock from source warehouse to destination warehouse
     * Save stock to journal and transfer stock
     * @param order order
     */
    private void updateTransferStocks(Order order) {
        log.info("Order with id: {} needs stock", order.getId().getValue());
        order.getStockTransfers().parallelStream().forEach(stock -> {
            int stockQuantity = stock.getQuantity();
            ProductId productId = stock.getProductId();
            Product product = productHelper.findProduct(productId);

            Stock warehouseTransferStock = warehouseStockHelper.findStockByProductIdAndWarehouseId(productId, stock.getSourceWarehouseId());
            Stock warehouseReceiveStock = warehouseStockHelper.findStockByProductIdAndWarehouseId(productId, order.getWarehouseId());

            StockTransferredUpdateEvent stockTransferredUpdateEvent = warehouseDomainService.
                    stockUpdatedAfterAutomaticallyTransfer(product, warehouseTransferStock, warehouseReceiveStock, stockQuantity, stockTransferredUpdateMessagePublisher);
            warehouseStockHelper.saveStockTransfer(stock);

            warehouseStockHelper.saveStock(warehouseTransferStock);
            StockJournal journalStock1 = warehouseDataMapper.stockToStockJournal(stockTransferredUpdateEvent.getStock());
            journalStock1.reduceStock(stockQuantity);
            warehouseStockHelper.saveStockJournal(journalStock1);

            warehouseStockHelper.saveStock(warehouseReceiveStock);
            StockJournal journalStock2 = warehouseDataMapper.stockToStockJournal(stockTransferredUpdateEvent.getStockReceived());
            journalStock2.addStock(stockQuantity);
            warehouseStockHelper.saveStockJournal(journalStock2);

            stockTransferredUpdateMessagePublisher.publish(stockTransferredUpdateEvent);
        });
    }

}
