package com.ecommerce.app.order.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.entity.Order;
import com.ecommerce.app.order.domain.core.entity.Product;
import com.ecommerce.app.order.domain.core.entity.User;
import com.ecommerce.app.order.domain.core.entity.Warehouse;
import com.ecommerce.app.order.domain.core.event.*;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent createOrder(Order order, List<Warehouse> warehouses, DomainEventPublisher<OrderCreatedEvent> domainEventPublisher);
    OrderPaymentProofUploadedEvent updateOrderPaymentStatus(Order order, DomainEventPublisher<OrderPaymentProofUploadedEvent> domainEventPublisher);
    OrderPaidEvent updateOrderStatusToPaid(Order order, DomainEventPublisher<OrderPaidEvent> domainEventPublisher);
    OrderProcessedEvent processOrder(Order order, DomainEventPublisher<OrderProcessedEvent> domainEventPublisher);
    OrderShippedEvent shipOrder(Order order, DomainEventPublisher<OrderShippedEvent> domainEventPublisher);
    OrderConfirmedEvent confirmOrder(Order order, DomainEventPublisher<OrderConfirmedEvent> domainEventPublisher);
}
