package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.payment.application.service.dto.message.*;
import com.ecommerce.app.payment.domain.core.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderHandler {

    private final OrderHelper orderHelper;

    public OrderHandler(OrderHelper orderHelper) {
        this.orderHelper = orderHelper;
    }

    public void createOrderPayment(PaymentRequest paymentRequest) {
        log.info("Creating order with id: {}", paymentRequest.getOrderId());
        Order createdOrder = orderHelper.createOrderPayment(paymentRequest);
        log.info("Order created with id: {}", createdOrder.getId().getValue());
    }

    public void updateOrderToPending(PaymentProofResponse paymentProofResponse) {
        log.info("Updating order with id: {} to pending", paymentProofResponse.getOrderId());
        Order updatedOrder = orderHelper.updateOrderToPending(paymentProofResponse);
        log.info("Order updated to pending with id: {}", updatedOrder.getId().getValue());
    }

    public void updateOrderToApprove(OrderPaidRequest orderPaidRequest) {
        log.info("Updating order with id: {} to approve", orderPaidRequest.getOrderId());
        Order updatedOrder = orderHelper.updateOrderToApproved(orderPaidRequest);
        log.info("Order updated to approve with id: {}", updatedOrder.getId().getValue());
    }

    public void updateOrderToProcessed(OrderProcessedRequest orderProcessedRequest) {
        log.info("Updating order with id: {} to processed", orderProcessedRequest.getOrderId());
        Order updatedOrder = orderHelper.updateOrderToProcessed(orderProcessedRequest);
        log.info("Order updated to processed with id: {}", updatedOrder.getId().getValue());
    }

    public void updateOrderToShipped(OrderShippedRequest orderShippedRequest) {
        log.info("Updating order with id: {} to shipped", orderShippedRequest.getOrderId());
        Order updatedOrder = orderHelper.updateOrderToShipped(orderShippedRequest);
        log.info("Order updated to shipped with id: {}", updatedOrder.getId().getValue());
    }

    public void updateOrderToConfirmed(OrderConfirmedRequest orderConfirmedRequest) {
        log.info("Updating order with id: {} to confirmed", orderConfirmedRequest.getOrderId());
        Order updatedOrder = orderHelper.updateOrderToConfirmed(orderConfirmedRequest);
        log.info("Order updated to confirmed with id: {}", updatedOrder.getId().getValue());
    }
}
