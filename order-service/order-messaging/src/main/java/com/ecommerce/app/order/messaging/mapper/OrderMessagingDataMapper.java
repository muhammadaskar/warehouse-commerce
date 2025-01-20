package com.ecommerce.app.order.messaging.mapper;

import com.ecommerce.app.common.domain.valueobject.PaymentStatus;
import com.ecommerce.app.kafka.warehouse.avro.model.*;
import com.ecommerce.app.order.application.service.dto.message.*;
import com.ecommerce.app.order.domain.core.entity.Order;
import com.ecommerce.app.order.domain.core.event.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public ProductCreatedRequest productCreatedRequestAvroModelToProductCreatedRequest(ProductCreatedRequestAvroModel productCreatedRequestAvroModel) {
        return ProductCreatedRequest.builder()
                .productId(productCreatedRequestAvroModel.getId().toString())
                .sku(productCreatedRequestAvroModel.getSku())
                .name(productCreatedRequestAvroModel.getName())
                .imageUrl(productCreatedRequestAvroModel.getImageUrl())
                .price(productCreatedRequestAvroModel.getPrice())
                .build();
    }

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {
        Order order = orderCreatedEvent.getOrder();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(order.getCreatedAt().toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setUserId(order.getUserId().getValue())
                .setOrderId(order.getId().getValue())
                .setWarehouseId(order.getWarehouseId().getValue())
                .setPrice(order.getTotalPrice().getAmount())
                .setPaymentOrderStatus(PaymentOrderStatus.AWAITING_PAYMENT)
                .setCreatedAt(zonedDateTime.toInstant())
                .build();
    }

    public PaymentProofUploadRequest paymentProofUploadRequestToPaymentProofUploadAvroModel(PaymentProofUploadAvroModel paymentProofUploadAvroModel) {
        return PaymentProofUploadRequest.builder()
                .orderId(paymentProofUploadAvroModel.getOrderId().toString())
                .paymentStatus(PaymentStatus.valueOf(paymentProofUploadAvroModel.getPaymentOrderStatus().toString()))
                .createdAt(paymentProofUploadAvroModel.getCreatedAt())
                .build();
    }

    public PaymentProofResponseAvroModel orderPaymentProofUploadedEventToPaymentProofResponseAvroModel(OrderPaymentProofUploadedEvent orderPaymentProofUploadedEvent) {
        Order order = orderPaymentProofUploadedEvent.getOrder();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(order.getUpdatedAt().toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        return PaymentProofResponseAvroModel.newBuilder()
                .setOrderId(order.getId().getValue())
                .setOrderStatus(OrderStatus.valueOf(order.getStatus().name()))
                .setUpdatedAt(zonedDateTime.toInstant())
                .build();
    }

    public PaymentApprovedRequest paymentApprovedRequestAvroModelToPaymentApprovedRequest(PaymentApprovedRequestAvroModel paymentApprovedRequestAvroModel) {
        return PaymentApprovedRequest.builder()
                .paymentId(paymentApprovedRequestAvroModel.getPaymentId().toString())
                .orderId(paymentApprovedRequestAvroModel.getOrderId().toString())
                .createdAt(paymentApprovedRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(PaymentStatus.valueOf(paymentApprovedRequestAvroModel.getPaymentOrderStatus().toString()))
                .build();
    }

    public OrderPaidRequestAvroModel orderPaidEventToOrderPaidRequestAvroModel(OrderPaidEvent orderPaidEvent) {
        Order order = orderPaidEvent.getOrder();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(order.getCreatedAt().toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        List<com.ecommerce.app.kafka.warehouse.avro.model.OrderItem> orderItems = order.getItems().stream()
                .map(orderItem -> com.ecommerce.app.kafka.warehouse.avro.model.OrderItem.newBuilder()
                        .setProductId(orderItem.getProductId().getValue())
                        .setQuantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrderPaidRequestAvroModel.newBuilder()
                .setOrderId(order.getId().getValue())
                .setWarehouseId(order.getWarehouseId().getValue())
                .setOrderItems(orderItems)
                .setCreatedAt(zonedDateTime.toInstant())
                .setOrderStatus(OrderStatus.valueOf(order.getStatus().name()))
                .build();
    }

    public OrderWarehouseResponse orderWarehouseResponseAvroModelToOrderWarehouseResponse(OrderWarehouseResponseAvroModel orderWarehouseResponseAvroModel) {
        return OrderWarehouseResponse.builder()
                .orderId(orderWarehouseResponseAvroModel.getOrderId().toString())
                .warehouseId(orderWarehouseResponseAvroModel.getWarehouseId().toString())
                .orderStatus(com.ecommerce.app.common.domain.valueobject.OrderStatus.valueOf(orderWarehouseResponseAvroModel.getOrderStatus().toString()))
                .createdAt(orderWarehouseResponseAvroModel.getCreatedAt())
                .build();
    }

    public OrderProcessedRequestAvroModel orderProcessedEventToOrderProcessedRequestAvroModel(OrderProcessedEvent orderProcessedEvent) {
        Order order = orderProcessedEvent.getOrder();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(order.getCreatedAt().toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return OrderProcessedRequestAvroModel.newBuilder()
                .setOrderId(order.getId().getValue())
                .setWarehouseId(order.getWarehouseId().getValue())
                .setOrderStatus(OrderStatus.valueOf(order.getStatus().toString()))
                .setCreatedAt(zonedDateTime.toInstant())
                .build();
    }

    public StockTransferredUpdate stockTransferredUpdateToStockTransferredUpdateAvroModel(StockTransferredUpdateAvroModel stockTransferredUpdateAvroModel) {
        return StockTransferredUpdate.builder()
                .stockIdWarehouseFrom(stockTransferredUpdateAvroModel.getStockIdWarehouseFrom().toString())
                .quantityUpdatedFromWarehouse(stockTransferredUpdateAvroModel.getQuantityUpdatedFromWarehouse())
                .stockIdWarehouseTo(stockTransferredUpdateAvroModel.getStockIdWarehouseTo().toString())
                .quantityUpdatedToWarehouse(stockTransferredUpdateAvroModel.getQuantityUpdatedToWarehouse())
                .updatedAt(stockTransferredUpdateAvroModel.getUpdatedAt())
                .build();
    }

    public OrderShippedRequestAvroModel orderShippedEventToOrderShippedRequestAvroModel(OrderShippedEvent orderShippedEvent) {
        Order order = orderShippedEvent.getOrder();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(order.getCreatedAt().toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        List<com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped> orderItems = order.getItems().stream()
                .map(orderItem -> com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.newBuilder()
                        .setProductId(orderItem.getProductId().getValue())
                        .setQuantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrderShippedRequestAvroModel.newBuilder()
                .setOrderId(order.getId().getValue())
                .setWarehouseId(order.getWarehouseId().getValue())
                .setOrderStatus(OrderStatus.valueOf(order.getStatus().toString()))
                .setCreatedAt(zonedDateTime.toInstant())
                .setOrderItems(orderItems)
                .build();
    }

    public StockShippedUpdate stockShippedUpdateToStockShippedUpdateAvroModel(StockShippedUpdateResponseAvroModel stockShippedUpdateAvroModel) {

        List<StockItem> stockItems = stockShippedUpdateAvroModel.getStockItems().stream()
                .map(stockItem -> StockItem.builder()
                        .stockId(stockItem.getStockId().toString())
                        .quantity(stockItem.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return StockShippedUpdate.builder()
                .stockItems(stockItems)
                .build();
    }

    public OrderConfirmedRequestAvroModel orderConfirmedRequestEventToOrderConfirmedRequestAvroModel(OrderConfirmedEvent orderConfirmedEvent) {
        Order order = orderConfirmedEvent.getOrder();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(order.getCreatedAt().toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        return OrderConfirmedRequestAvroModel.newBuilder()
                .setOrderId(order.getId().getValue())
                .setWarehouseId(order.getWarehouseId().getValue())
                .setOrderStatus(OrderStatus.valueOf(order.getStatus().toString()))
                .setCreatedAt(zonedDateTime.toInstant())
                .build();
    }

    public StockUpdated stockUpdatedRequestAvroModelToStockUpdated(StockUpdatedRequestAvroModel stockUpdatedRequestAvroModel) {
        return StockUpdated.builder()
                .stockId(stockUpdatedRequestAvroModel.getStockId().toString())
                .quantity(stockUpdatedRequestAvroModel.getQuantity())
                .createdAt(ZonedDateTime.ofInstant(stockUpdatedRequestAvroModel.getCreatedAt(), ZoneId.systemDefault()))
                .build();
    }

    public StockCreated stockCreatedRequestAvroModelToStockCreated(StockCreatedRequestAvroModel stockCreatedRequestAvroModel) {
        return StockCreated.builder()
                .stockId(stockCreatedRequestAvroModel.getStockId().toString())
                .warehouseId(stockCreatedRequestAvroModel.getWarehouseId().toString())
                .productId(stockCreatedRequestAvroModel.getProductId().toString())
                .quantity(stockCreatedRequestAvroModel.getQuantity())
                .createdAt(ZonedDateTime.ofInstant(stockCreatedRequestAvroModel.getCreatedAt(), ZoneId.systemDefault()))
                .build();
    }
}
