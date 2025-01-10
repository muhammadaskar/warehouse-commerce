package com.ecommerce.app.warehouse.messaging.mapper;

import com.ecommerce.app.kafka.warehouse.avro.model.*;
import com.ecommerce.app.warehouse.domain.core.entity.Order;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.*;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderItem;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderPaidRequest;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderShippedRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class WarehouseMessagingDataMapper {

    public WarehouseCreateAvroModel warehouseCreatedEventToOtherRequestAvroModel(WarehouseCreatedEvent warehouseCreatedEvent) {
        Warehouse warehouse = warehouseCreatedEvent.getWarehouse();
        return WarehouseCreateAvroModel.newBuilder()
                .setWarehouseId(warehouse.getId().getValue())
                .build();
    }

    public OrderPaidRequest orderPaidRequestAvroModelToOrderPaidRequest(OrderPaidRequestAvroModel orderPaidRequestAvroModel) {
        List<OrderItem> orderItems = orderPaidRequestAvroModel.getOrderItems().stream()
                .map(orderItem -> OrderItem.builder()
                        .productId(orderItem.getProductId().toString())
                        .quantity(orderItem.getQuantity())
                        .build())
                .toList();

        return OrderPaidRequest.builder()
                .orderId(orderPaidRequestAvroModel.getOrderId().toString())
                .warehouseId(orderPaidRequestAvroModel.getWarehouseId().toString())
                .orderStatus(orderPaidRequestAvroModel.getOrderStatus().toString())
                .orderItems(orderItems)
                .build();
    }

    public OrderWarehouseResponseAvroModel orderWarehouseEventToOrderWarehouseResponseAvroModel(OrderWarehouseResponseEvent event) {
        Order order = event.getOrder();
        return OrderWarehouseResponseAvroModel.newBuilder()
                .setOrderId(order.getId().getValue())
                .setWarehouseId(order.getWarehouseId().getValue())
                .setOrderStatus(OrderStatus.valueOf(order.getOrderStatus().toString()))
                .setCreatedAt(Instant.now())
                .build();
    }

    public StockTransferredUpdateAvroModel stockTransferredUpdateEventToStockTransferredUpdateAvroModel(StockTransferredUpdateEvent event) {
        Stock warehouseTransferStock = event.getStock();
        Stock warehouseReceivedStock = event.getStockReceived();
        ZonedDateTime transferDate = ZonedDateTime.parse(event.getCreatedAt().toString(), DateTimeFormatter.ISO_DATE_TIME);
        return StockTransferredUpdateAvroModel.newBuilder()
                .setStockIdWarehouseFrom(warehouseTransferStock.getId().getValue())
                .setQuantityUpdatedFromWarehouse(warehouseTransferStock.getQuantity())
                .setStockIdWarehouseTo(warehouseReceivedStock.getId().getValue())
                .setQuantityUpdatedToWarehouse(warehouseReceivedStock.getQuantity())
                .setUpdatedAt(transferDate.toInstant())
                .build();
    }

    public OrderShippedRequest orderShippedRequestAvroModelToOrderShippedRequest(OrderShippedRequestAvroModel orderShippedRequestAvroModel) {
        List<OrderItem> orderItems = orderShippedRequestAvroModel.getOrderItems().stream()
                .map(orderItem -> OrderItem.builder()
                        .productId(orderItem.getProductId().toString())
                        .quantity(orderItem.getQuantity())
                        .build())
                .toList();

        return OrderShippedRequest.builder()
                .orderId(orderShippedRequestAvroModel.getOrderId().toString())
                .warehouseId(orderShippedRequestAvroModel.getWarehouseId().toString())
                .orderStatus(orderShippedRequestAvroModel.getOrderStatus().toString())
                .orderItems(orderItems)
                .build();
    }

    public OrderShippedResponseAvroModel orderShippedEventToOrderShippedResponseAvroModel(OrderShippedResponseEvent event) {
        Order order = event.getOrder();

        return OrderShippedResponseAvroModel.newBuilder()
                .setOrderId(order.getId().getValue())
                .setWarehouseId(order.getWarehouseId().getValue())
                .setOrderStatus(OrderStatus.valueOf(order.getOrderStatus().toString()))
                .setCreatedAt(Instant.now())
                .build();
    }

    public StockShippedUpdateResponseAvroModel stockShippedUpdateEventToStockShippedUpdateResponseAvroModel(StockShippedUpdateEvent event) {
        List<StockItemShipped> stocks = event.getStocks().stream()
                .map(stock -> StockItemShipped.newBuilder()
                        .setStockId(stock.getId().getValue())
                        .setQuantity(stock.getQuantity())
                        .build())
                .toList();
        return StockShippedUpdateResponseAvroModel.newBuilder()
                .setStockItems(stocks)
                .build();
    }

    public StockUpdatedRequestAvroModel stockUpdatedEventToStockUpdatedRequestAvroModel(StockUpdatedEvent event) {
        Stock stock = event.getStock();
        ZonedDateTime createdAt = ZonedDateTime.parse(event.getCreatedAt().toString(), DateTimeFormatter.ISO_DATE_TIME);
        return StockUpdatedRequestAvroModel.newBuilder()
                .setStockId(stock.getId().getValue())
                .setQuantity(stock.getQuantity())
                .setCreatedAt(createdAt.toInstant())
                .build();
    }

    public StockCreatedRequestAvroModel stockCreatedEventToStockCreatedRequestAvroModel(StockCreatedEvent event) {
        Stock stock = event.getStock();
        ZonedDateTime createdAt = ZonedDateTime.parse(event.getCreatedAt().toString(), DateTimeFormatter.ISO_DATE_TIME);
        return StockCreatedRequestAvroModel.newBuilder()
                .setStockId(stock.getId().getValue())
                .setWarehouseId(stock.getWarehouseId().getValue())
                .setProductId(stock.getProductId().getValue())
                .setQuantity(stock.getQuantity())
                .setCreatedAt(createdAt.toInstant())
                .build();
    }
}
