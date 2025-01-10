package com.ecommerce.app.payment.application.service.mapper;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.payment.application.service.dto.message.*;
import com.ecommerce.app.payment.domain.core.entity.Order;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderDataMapper {

    public Order paymentRequestToOrder(PaymentRequest paymentRequest) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .withUserId(new UserId(UUID.fromString(paymentRequest.getUserId())))
                .withWarehouseId(new WarehouseId(UUID.fromString(paymentRequest.getWarehouseId())))
                .withPayment(paymentRequestToPayment(paymentRequest))
                .withStatus(OrderStatus.AWAITING_PAYMENT)
                .build();
    }

    public Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return Payment.newBuilder()
                .withId(new PaymentId(UUID.fromString(paymentRequest.getId())))
                .withOrderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .withAmount(new Money(paymentRequest.getPrice()))
                .withStatus(PaymentStatus.AWAITING_PAYMENT)
                .build();
    }

    public PaymentRequest orderToPaymentRequest(Order order) {
        return PaymentRequest.builder()
                .orderId(order.getId().getValue().toString())
                .userId(order.getUserId().getValue().toString())
                .paymentStatus(PaymentStatus.valueOf(order.getStatus().name()))
                .build();
    }

    public Order paymentProofResponseToOrder(PaymentProofResponse paymentProofResponse) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(paymentProofResponse.getOrderId())))
                .withStatus(OrderStatus.valueOf(paymentProofResponse.getOrderStatus()))
                .build();
    }

    public Order orderPaidRequestToOrder(OrderPaidRequest orderPaidRequest) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(orderPaidRequest.getOrderId())))
                .withStatus(OrderStatus.valueOf(orderPaidRequest.getOrderStatus()))
                .build();
    }

    public Order orderProcessedRequestToOrder(OrderProcessedRequest orderProcessedRequest) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(orderProcessedRequest.getOrderId())))
                .withStatus(OrderStatus.valueOf(orderProcessedRequest.getOrderStatus()))
                .build();
    }

    public Order orderShippedRequestToOrder(OrderShippedRequest orderShippedRequest) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(orderShippedRequest.getOrderId())))
                .withStatus(OrderStatus.valueOf(orderShippedRequest.getOrderStatus()))
                .build();
    }

    public Order orderConfirmedRequestToOrder(OrderConfirmedRequest orderConfirmedRequest) {
        return Order.newBuilder()
                .withId(new OrderId(UUID.fromString(orderConfirmedRequest.getOrderId())))
                .withStatus(OrderStatus.valueOf(orderConfirmedRequest.getOrderStatus()))
                .build();
    }
}
