package com.ecommerce.app.order.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.entity.Order;
import com.ecommerce.app.order.domain.core.entity.Warehouse;
import com.ecommerce.app.order.domain.core.event.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class OrderDomainServiceImpl implements OrderDomainService{

    @Override
    public OrderCreatedEvent createOrder(Order order, List<Warehouse> warehouses, DomainEventPublisher<OrderCreatedEvent> domainEventPublisher) {
        order.validateOrder();
        order.initializeOrder(warehouses);
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")), domainEventPublisher);
    }

    @Override
    public OrderPaymentProofUploadedEvent updateOrderPaymentStatus(Order order, DomainEventPublisher<OrderPaymentProofUploadedEvent> domainEventPublisher) {
        order.awaitingPaymentToPending();
        return new OrderPaymentProofUploadedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")), domainEventPublisher);
    }

    @Override
    public OrderPaidEvent updateOrderStatusToPaid(Order order, DomainEventPublisher<OrderPaidEvent> domainEventPublisher) {
        order.payOrder();
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")), domainEventPublisher);
    }

    @Override
    public OrderProcessedEvent processOrder(Order order, DomainEventPublisher<OrderProcessedEvent> domainEventPublisher) {
        order.processedOrder();
        return new OrderProcessedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")), domainEventPublisher);
    }

    @Override
    public OrderShippedEvent shipOrder(Order order, DomainEventPublisher<OrderShippedEvent> domainEventPublisher) {
        order.shippedOrder();
        return new OrderShippedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")), domainEventPublisher);
    }

    @Override
    public OrderConfirmedEvent confirmOrder(Order order, DomainEventPublisher<OrderConfirmedEvent> domainEventPublisher) {
        order.confirmOrder();
        return new OrderConfirmedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")), domainEventPublisher);
    }
}
