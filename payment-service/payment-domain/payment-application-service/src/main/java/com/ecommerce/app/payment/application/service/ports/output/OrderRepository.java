package com.ecommerce.app.payment.application.service.ports.output;

import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.payment.domain.core.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId orderId);
}
