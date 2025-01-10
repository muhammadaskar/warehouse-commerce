package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.payment.application.service.dto.message.*;
import com.ecommerce.app.payment.application.service.ports.input.message.listener.order.OrderApplicationMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Component
public class OrderApplicationMessageListenerImpl implements OrderApplicationMessageListener {

    private final OrderHandler orderHandler;

    public OrderApplicationMessageListenerImpl(OrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Override
    public void updateOrderStatusToPending(PaymentProofResponse paymentProofResponse) {
        log.info("Received payment proof response: {}", paymentProofResponse);
        orderHandler.updateOrderToPending(paymentProofResponse);
    }

    @Override
    public void updateOrderStatusToApproved(OrderPaidRequest orderPaidRequest) {
        log.info("Received order paid request: {}", orderPaidRequest);
        orderHandler.updateOrderToApprove(orderPaidRequest);
    }

    @Override
    public void updateOrderStatusToProcessed(OrderProcessedRequest orderProcessedRequest) {
        log.info("Received order processed request: {}", orderProcessedRequest);
        orderHandler.updateOrderToProcessed(orderProcessedRequest);
    }

    @Override
    public void updateOrderStatusToShipped(OrderShippedRequest orderShippedRequest) {
        log.info("Received order shipped request: {}", orderShippedRequest);
        orderHandler.updateOrderToShipped(orderShippedRequest);
    }

    @Override
    public void updateOrderStatusToConfirmed(OrderConfirmedRequest orderConfirmedRequest) {
        log.info("Received order confirmed request: {}", orderConfirmedRequest);
        orderHandler.updateOrderToConfirmed(orderConfirmedRequest);
    }
}
